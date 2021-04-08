package org.manger.backend.siteExtensions;


import java.io.IOException;
import java.util.List;

public interface MangaWebsite {
    /*
    * Used to download the entire website.
    * Then parse only manga titles and return them as a list of strings.
    */
    public List<String> loadListOfAllMangas() throws IOException, InterruptedException;
}
