package org.manger.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.manger.backend.siteExtensions.MangaInfo;

import java.io.IOException;

public class MainWindowController {
    @FXML private ListView<String> mangaList;
    @FXML private TableColumn<MangaInfo, Image> imageColumn;
    @FXML private TableColumn<MangaInfo, String> titleColumn;
    @FXML private TextField searchBar;
    @FXML private final Stage stage;

    private final DataStorage storage;


    public MainWindowController(DataStorage storage) throws IOException {
        this.storage = storage;
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainWindow.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Manger");
        stage.show();

        scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());

        initSearchBarListener();
    }

    public void fillMangaList() {
        for(MangaInfo info : storage.getAllMangas()) {
            mangaList.getItems().add(info.getTitle());
        }
    }

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
        mangaList.getItems().clear();
        for(MangaInfo info : storage.getAllMangas()) {
            String title = info.getTitle();
            if(title.toLowerCase().contains(newValue.toLowerCase())) {
                mangaList.getItems().add(title);
            } else if(!info.getAlternatives().isEmpty()) {
                for(String alternativeTitle : info.getAlternatives()) {
                    if(alternativeTitle.toLowerCase().contains(newValue.toLowerCase())) {
                        mangaList.getItems().add(title + " (" + alternativeTitle + ")");
                    }
                }
            }
        }
    }
}
