package org.manger.frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.manger.backend.siteExtensions.MangaInfo;

import java.util.ArrayList;
import java.util.List;

public class MainWindowController {
    // Components in the "Browse" section
    @FXML private Pane browsePane;
    @FXML private ListView<String> mangaList;
    @FXML private TextField searchBar;
    @FXML private ListView<String> genreList;

    // Components in the "Source" section
    @FXML private Pane sourcePane;

    private DataStorage storage;
    private List<String> wantedGenres = new ArrayList<>();
    private List<String> unwantedGenres = new ArrayList<>();

    /**
     * Imitates a cunstructor.
     * This is the only solution I've been able to find to fix FXML controller issues.
     */
    public void init() {
        initMainButtons();
        fillMangaList();
        initSearchBarListener();
        initGenreList();
    }

    private void initMainButtons() {
//        libraryButton.setGraphic(new Image());
    }

    public void fillMangaList() {
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
     * TODO:
     * - write comments about each function connected with genre search
     * - FIX: filtering with genres and searching with text doesn't work at the same time
     */
    private void initGenreListListener() {
        genreList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int selectedGenreIndex = genreList.getSelectionModel().getSelectedIndex();
                String selectedGenre = genreList.getSelectionModel().getSelectedItem();
                Object[] cells = genreList.lookupAll(".cell").toArray();
                Cell<String> cell = (Cell) cells[selectedGenreIndex];

                // Visual color change
                ObservableList<String> CSSclasses = cell.getStyleClass();
                if(CSSclasses.size() == 3) {
                    CSSclasses.add("genreYes");

                    wantedGenres.add(selectedGenre);
                    showMangasWithGenre(selectedGenre);
                } else if(CSSclasses.get(3).equals("genreYes")) {
                    CSSclasses.remove(3);
                    CSSclasses.add("genreNo");

                    wantedGenres.remove(selectedGenre);
                    unwantedGenres.add(selectedGenre);
                    restoreRemovedMangas();
                    removeMangasWithGenre(selectedGenre);
                } else {
                    CSSclasses.remove(3);

                    unwantedGenres.remove(selectedGenre);
                    restoreRemovedMangas();
                }
                System.out.println(storage.getFilteredMangas().size());
            }
        });
    }

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

        System.out.println(storage.getFilteredMangas().size());
    }

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
        for(MangaInfo info : storage.getAllMangas()) {
            String title = info.getTitle();
            if(title.toLowerCase().contains(lowerCaseUserInput)) {
                if(!mangaList.getItems().contains(title)) {
                    mangaList.getItems().add(title);
                    storage.getFilteredMangas().add(info);
                }
            } else if(!info.getAlternatives().isEmpty()) {
                for(String alternativeTitle : info.getAlternatives()) {
                    if(alternativeTitle.toLowerCase().contains(lowerCaseUserInput)) {
                        if(!mangaList.getItems().contains(title)) {
                            mangaList.getItems().add(title + " (" + alternativeTitle + ")");
                            storage.getFilteredMangas().add(info);
                        }
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

    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }
}