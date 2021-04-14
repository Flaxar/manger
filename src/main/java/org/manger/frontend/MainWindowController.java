package org.manger.frontend;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.manger.backend.siteExtensions.MangaInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindowController {
    // Components in the "Browse" section
    @FXML private Pane browsePane;
    @FXML private ListView<String> mangaList;
    @FXML private TextField searchBar;
    @FXML private ListView<String> genreList;

    // Components in the "Source" section
    @FXML private Pane sourcePane;

    // Components in the "Single Manga Browse"
    @FXML private Pane singleMangaPane;
    @FXML private ImageView mangaCover;
    @FXML private ListView<String> chapterList;

    private DataStorage storage;
    private List<String> wantedGenres = new ArrayList<>();
    private List<String> unwantedGenres = new ArrayList<>();

    /**
     * Imitates a cunstructor.
     * This is the only solution I've been able to find to fix FXML controller issues.
     */
    public void init() {
        initMangaList();
        initSearchBarListener();
        initGenreList();
    }

    public void initMangaList() {
        for(MangaInfo info : storage.getAllMangas()) {
            mangaList.getItems().add(info.getTitle());
            storage.getFilteredMangas().add(info);
        }

        
    }

    private void initGenreList() {
        for(String genre : storage.getGenres()) {
            genreList.getItems().add(genre);
        }
        initGenreListListener();
    }


    /**
     * Sets on mouse clicked event to genre list view. Everytime a genre is clicked
     * it's state is changed - visually by color. When the state of a genre is changed,
     * apropriate function for sorting by genre is called.
     */
    private void initGenreListListener() {
        genreList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int selectedGenreIndex = genreList.getSelectionModel().getSelectedIndex();
                String selectedGenre = genreList.getSelectionModel().getSelectedItem();
                Object[] cells = genreList.lookupAll(".cell").toArray();
                Cell<String> cell = (Cell) cells[selectedGenreIndex];

                ObservableList<String> CSSclasses = cell.getStyleClass();
                if(CSSclasses.size() == 3) {
                    // This code executes when genre's state is set to WANTED
                    CSSclasses.add("genreYes");

                    wantedGenres.add(selectedGenre);
                    showMangasWithGenre(selectedGenre);
                } else if(CSSclasses.get(3).equals("genreYes")) {
                    // This code executes when genre's state is set to UNWANTED
                    CSSclasses.remove(3);
                    CSSclasses.add("genreNo");

                    wantedGenres.remove(selectedGenre);
                    unwantedGenres.add(selectedGenre);
                    restoreRemovedMangas();
                    removeMangasWithGenre(selectedGenre);
                } else {
                    // This code executes when genre's state reset to default
                    CSSclasses.remove(3);

                    unwantedGenres.remove(selectedGenre);
                    restoreRemovedMangas();
                }
            }
        });
    }

    /**
     * Removes all mangas that contain removingGenre in their genres from
     * storage.filteredMangas and mangaList.
     * @param removingGenre is the genre you have set as UNWANTED (with red color)
     */
    private void removeMangasWithGenre(String removingGenre) {
        List<MangaInfo> mangasToRemove = new ArrayList<>();
        for(MangaInfo manga : storage.getFilteredMangas()) {
            if(manga.getGenres().contains(removingGenre)) {
                mangaList.getItems().remove(manga.getTitle());
                mangasToRemove.add(manga);
            }
        }
        storage.getFilteredMangas().removeAll(mangasToRemove);
    }

    /**
     * Compares storage.allMangas and storage.filteredMangas and
     * returns them back to storage.filteredMangas and mangaList.
     */
    private void restoreRemovedMangas() {
        storage.getFilteredMangas().clear();
        storage.getFilteredMangas().addAll(storage.getAllMangas());

        mangaList.getItems().clear();
        for(MangaInfo manga : storage.getAllMangas()) {
            mangaList.getItems().add(manga.getTitle());
        }

        for(String genre : wantedGenres) {
            showMangasWithGenre(genre);
        }

        for(String genre : unwantedGenres) {
            removeMangasWithGenre(genre);
        }
    }

    /**
     * Removed all mangas that don't contain newGenre as their genre from
     * storage.filteredMangas and mangaList.
     * @param newGenre is the genre you have set as WANTED (with green color)
     */
    private void showMangasWithGenre(String newGenre) {
        List<MangaInfo> mangasToRemove = new ArrayList<>();
        for(MangaInfo manga : storage.getFilteredMangas()) {
            if(!manga.getGenres().contains(newGenre)) {
                mangaList.getItems().remove(manga.getTitle());
                mangasToRemove.add(manga);
            }
        }
        storage.getFilteredMangas().removeAll(mangasToRemove);
    }

    /**
     * Adds a listener to the search bar.
     * Everytime the text is changed, filterList is called with the new
     * text as an argument.
     */
    private void initSearchBarListener() {
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filterList(newValue)
        );
    }

    /**
     * Compares newValue with every title of every manga in manga storage.
     * If the title doesn't contain newValue, then it tries to find the
     * title in alternative titles of the manga.
     * If an alternative title is found, then it's displayed as:
     * manga_title (matching_alternative_title)
     * @param newValue string value, that user typed into the search bar
     */
    private void filterList(String newValue) {
        String lowerCaseUserInput = newValue.toLowerCase();
        for(MangaInfo info : storage.getFilteredMangas()) {
            String title = info.getTitle();
            if(!title.toLowerCase().contains(lowerCaseUserInput)) {
                mangaList.getItems().remove(title);
            } else if(!mangaList.getItems().contains(title)) {
                mangaList.getItems().add(title);
            } else if(!info.getAlternatives().isEmpty()) {
                for(String alternativeTitle : info.getAlternatives()) {
                    if(!title.toLowerCase().contains(lowerCaseUserInput)) {
                        mangaList.getItems().remove(title);
                    } else if(!mangaList.getItems().contains(title)) {
                        mangaList.getItems().add(title + " (" + alternativeTitle + ")");
                    }
                }
            }
        }
    }

    public void switchToLibraryPane() {

    }

    public void switchToBrowsePane() {
        sourcePane.setVisible(false);
        sourcePane.setDisable(true);

        browsePane.setVisible(true);
        browsePane.setDisable(false);
    }

    public void switchToSourcesPane() {
        sourcePane.setVisible(true);
        sourcePane.setDisable(false);

        browsePane.setVisible(false);
        browsePane.setDisable(true);
    }

    public void switchToSettingsPane() {

    }

    public void switchToSingleMangaPane(MangaInfo manga) {
        sourcePane.setVisible(false);
        sourcePane.setDisable(true);

        browsePane.setVisible(false);
        browsePane.setDisable(true);

        singleMangaPane.setVisible(true);
        singleMangaPane.setDisable(false);

        showNewSingleMangaInfo(manga);
    }

    private void showNewSingleMangaInfo(MangaInfo manga) {
        Image cover = new Image("https://cover.nep.li/cover/" + manga.getHeadURL() + ".jpg");
        mangaCover.setImage(cover);

        List<Chapter> chapters = fetchChapters(manga);
//        for(Chapter chapter : fetchChapters(manga)) {
//            System.out.println(chapter.getChapter());
//        }
    }

    private List<Chapter> fetchChapters(MangaInfo manga) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://mangasee123.com/manga/" + manga.getHeadURL()).maxBodySize(5000000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements scriptElements = doc.getElementsByTag("script");
        String unparsedScript = scriptElements.html();

        Pattern pattern = Pattern.compile("vm.Chapters = ([^\\n]*);");
        Matcher matcher = pattern.matcher(unparsedScript);
        matcher.find();
        String unparsedJson = matcher.group(0);


        unparsedJson = unparsedJson.replace("vm.Chapters = ", "");
        unparsedJson = unparsedJson.substring(0, unparsedJson.length() - 1);
        System.out.println(unparsedJson);
        Gson gson = new Gson();
        Chapter[] chapters = gson.fromJson(unparsedJson, Chapter[].class);

        for(Chapter chapter : chapters) {
            System.out.println(chapter.getChapter());
        }

        return Arrays.asList(chapters);
    }

    public void handleListItemClicked(MouseEvent mouseEvent) {
//        System.out.println("hey");
        switchToSingleMangaPane(storage.getAllMangas().get(mangaList.getSelectionModel().getSelectedIndex()));
    }

    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }
}