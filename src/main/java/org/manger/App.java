package org.manger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.manger.backend.WebLoader;
import org.manger.backend.siteExtensions.Mangasee;
import org.manger.frontend.DataStorage;
import org.manger.frontend.MainWindowController;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        WebLoader webLoader = new WebLoader();
        webLoader.loadGenres();
//        WebLoader.downloadMangaList("https://myanimelist.net/mangalist/Flaxar?status=1");
        DataStorage storage = new DataStorage();
        storage.setAllMangas(webLoader.loadListOfAllMangas());
        storage.setGenres(webLoader.loadGenres());


        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainWindow.fxml"));
        Scene scene = new Scene(loader.load());
        MainWindowController mainWindowController = loader.getController();
        stage.setScene(scene);
        stage.setTitle("Manger");
        stage.show();

        mainWindowController.setStorage(storage);
        mainWindowController.setWebLoader(webLoader);
        mainWindowController.init();

        scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
    }
}