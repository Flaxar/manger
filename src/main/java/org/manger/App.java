package org.manger;

import org.manger.backend.WebLoader;
import org.manger.frontend.UserInterfaceController;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        UserInterfaceController.launchMainWindow();

        WebLoader.downloadMangaList("https://myanimelist.net/mangalist/Flaxar?status=1");
    }
}