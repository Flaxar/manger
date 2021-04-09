package org.manger.backend.siteExtensions;

import javafx.collections.ObservableArray;
import javafx.scene.image.Image;

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
        cover = new Image("https://cover.nep.li/cover/" + i);
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

    public Image getCover() {
        return cover;
    }
}
