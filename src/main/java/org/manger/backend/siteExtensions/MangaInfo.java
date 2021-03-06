package org.manger.backend.siteExtensions;

import javafx.collections.ObservableArray;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MangaInfo {
    String i; // title for URLs
    String s; // main title
    String o;
    String ss;
    String ps;
    String t; // type (manga, manhwa, ...)
    String v;
    String vm;
    String y;
    List<String> a;
    List<String> al;
    String l;
    int lt;
    String ls;
    List<String> g; // genres of the manga
    boolean h;

    List<String> categories = new ArrayList<>(); // categories added by the user

    Image cover;

    public MangaInfo(String i, String s, List<String> a) {
        this.i = i;
        this.s = s;
        this.a = a;
    }

    public String getHeadURL() {
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

    public List<String> getGenres() {
        return g;
    }

    public String getType() {
        return t;
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public boolean containsCategory(String category) {
        return categories.contains(category);
    }

    public void removeCategory(String category) {
        categories.remove(category);
    }
}
