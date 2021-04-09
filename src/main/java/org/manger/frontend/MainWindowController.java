package org.manger.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.manger.backend.siteExtensions.MangaInfo;

import java.io.IOException;

public class MainWindowController {
    @FXML private ListView<String> mangaList;
    @FXML private TableColumn<MangaInfo, Image> imageColumn;
    @FXML private TableColumn<MangaInfo, String> titleColumn;
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
    }

    public void fillMangaList() {
        for(MangaInfo info : storage.getAllMangas()) {
            mangaList.getItems().add(info.getTitle());
        }
    }
}
