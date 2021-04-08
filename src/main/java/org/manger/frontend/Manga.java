package org.manger.frontend;

import javafx.scene.image.Image;

public class Manga {
    String title;
    Image image;

    Manga(String title, String imageURL) {
        this.title = title;
        this.image = new Image(imageURL);
    }
}
