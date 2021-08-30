package org.manger.frontend;

import org.manger.backend.DatabaseController;
import org.manger.backend.WebLoader;
import org.manger.backend.siteExtensions.MangaInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStorage {
    List<MangaInfo> allMangas;
    List<MangaInfo> filteredMangas = new ArrayList<>();
    HashMap<String, MangaInfo> mangaMap = new HashMap<>();
    List<String> genres;
    String selectedCategory;
    DatabaseController databaseController;

    public DataStorage(WebLoader loader) {
        databaseController = new DatabaseController();
        databaseController.setLoader(loader);
    }

    public List<MangaInfo> getAllMangas() {
        return allMangas;
    }

    public void setAllMangas(List<MangaInfo> allMangas) {
        this.allMangas = allMangas;
        for(MangaInfo manga : allMangas) {
            mangaMap.put(manga.getTitle(), manga);
        }
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

    public MangaInfo getMangaByTitle(String mangaTitle) {
        return mangaMap.get(mangaTitle);
    }

    public void setFilteredMangas(List<MangaInfo> filteredMangas) {
        this.filteredMangas = filteredMangas;
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
