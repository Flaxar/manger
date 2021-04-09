package org.manger;

import javafx.application.Application;
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
        Mangasee mangasee = new Mangasee(); // TODO: Nacitani pres WebLoader, ne primo z Mangasee
        mangasee.loadGenres();
//        WebLoader.downloadMangaList("https://myanimelist.net/mangalist/Flaxar?status=1");
        DataStorage storage = new DataStorage();
        storage.setAllMangas(mangasee.loadListOfAllMangas());

        MainWindowController mainController = new MainWindowController(storage);
        mainController.fillMangaList();
    }
}