package org.manger.backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ParallelLoader extends Thread{
    String URL;
    ImageView mangaCover;

    public ParallelLoader(String mangaHeaderURL, ImageView mangaCover) {
        URL = "https://cover.nep.li/cover/" + mangaHeaderURL + ".jpg";
        this.mangaCover = mangaCover;
    }

    public void run() {
        Image coverImage = new Image(URL);
        mangaCover.setImage(coverImage);
    }
}
