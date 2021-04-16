package org.manger.backend.siteExtensions;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.manger.frontend.Chapter;

import java.io.IOException;
import java.util.List;

public interface MangaWebsite {
    /*
    * Used to download the entire website.
    * Then parse it into a List of MangaInfo classes
    */
    public List<MangaInfo> loadListOfAllMangas() throws IOException, InterruptedException;

    public List<String> loadGenres();

    public void loadMangaCover(ImageView imageView, MangaInfo manga);

    public List<Chapter> getMangaChapters(MangaInfo manga);
}
