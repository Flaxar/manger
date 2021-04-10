package org.manger.frontend;

import org.manger.backend.siteExtensions.MangaInfo;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    List<MangaInfo> allMangas;
    List<MangaInfo> filteredMangas = new ArrayList<>();
    List<String> genres;

    public List<MangaInfo> getAllMangas() {
        return allMangas;
    }

    public void setAllMangas(List<MangaInfo> allMangas) {
        this.allMangas = allMangas;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<MangaInfo> getFilteredMangas() {
        return filteredMangas;
    }

    public void setFilteredMangas(List<MangaInfo> filteredMangas) {
        this.filteredMangas = filteredMangas;
    }
}
