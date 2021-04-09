package org.manger.backend.siteExtensions;


import java.io.IOException;
import java.util.List;

public interface MangaWebsite {
    /*
    * Used to download the entire website.
    * Then parse it into a List of MangaInfo classes
    */
    public List<MangaInfo> loadListOfAllMangas() throws IOException, InterruptedException;

    public void loadGenres();

    public List<MangaInfo> getMangasByGenre();
}
