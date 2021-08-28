package org.manger.backend.siteExtensions;

import javafx.scene.image.ImageView;
import org.manger.frontend.Chapter;

import java.io.IOException;
import java.util.List;

public interface MangaWebsite {
    /*
    * Used to download the entire website.
    * Then parse it into a List of MangaInfo classes
    */
    List<MangaInfo> loadListOfAllMangas() throws IOException, InterruptedException;

    List<String> loadGenres();

    void loadMangaCover(ImageView imageView, MangaInfo manga);

    List<Chapter> getMangaChapters(MangaInfo manga);

    void openMangaInBrowser(MangaInfo manga);
}
