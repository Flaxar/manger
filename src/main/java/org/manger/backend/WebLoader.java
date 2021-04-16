package org.manger.backend;

import javafx.scene.image.ImageView;
import org.manger.backend.siteExtensions.MangaInfo;
import org.manger.backend.siteExtensions.MangaWebsite;
import org.manger.backend.siteExtensions.Mangasee;
import org.manger.frontend.Chapter;

import java.io.IOException;
import java.util.List;

public class WebLoader {
    MangaWebsite defaultWebsite;

    public WebLoader() {
        defaultWebsite = new Mangasee();
    }

    public List<MangaInfo> loadListOfAllMangas() {
        try {
            return defaultWebsite.loadListOfAllMangas();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> loadGenres() {
        return defaultWebsite.loadGenres();
    }

    public void loadMangaCover(ImageView imageView, MangaInfo manga) {
        defaultWebsite.loadMangaCover(imageView, manga);
    }

    public List<Chapter> getMangaChapters(MangaInfo manga) {
        return defaultWebsite.getMangaChapters(manga);
    }
}
