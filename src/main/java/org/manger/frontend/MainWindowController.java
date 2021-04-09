package org.manger.frontend;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.manger.backend.siteExtensions.MangaInfo;

import java.io.IOException;
import java.util.List;

public class MainWindowController {
    @FXML private TableView<Manga> mangaList;
    @FXML private TableColumn<MangaInfo, Image> imageColumn;
    @FXML private TableColumn<MangaInfo, String> titleColumn;
    @FXML private final Stage stage;


    public MainWindowController() throws IOException {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainWindow.fxml"));
        loader.setController(this);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Manger");
        stage.show();
    }

    public void fillMangaList() {
        // TODO: FIX
        ObservableList<MangaInfo> observableMangas = FXCollections.observableArrayList();
    }
}
