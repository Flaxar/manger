package org.manger.backend.siteExtensions;

import java.util.List;

public class MangaInfo {
    String i;
    String s;
    List<String> a;

    public MangaInfo(String i, String s, List<String> a) {
        this.i = i;
        this.s = s;
        this.a = a;
    }

    public String getTitle() {
        return i;
    }

    public String getTransalation() {
        return s;
    }

    public List<String> getAlternatives() {
        return a;
    }
}
