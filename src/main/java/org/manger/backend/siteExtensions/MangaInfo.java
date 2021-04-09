package org.manger.backend.siteExtensions;

import javafx.collections.ObservableArray;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class MangaInfo {
    String i;
    String s;
    List<String> a;

    Image cover;

    public MangaInfo(String i, String s, List<String> a) {
        this.i = i;
        this.s = s;
        this.a = a;
    }

    public String getURL() {
        return i;
    }

    public String getTitle() {
        return s;
    }

    public List<String> getAlternatives() {
        return a;
    }

    public ImageView getImage() {
        cover = new Image("https://cover.nep.li/cover/" + i);
        return new ImageView(cover);
    }
}
