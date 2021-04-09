package org.manger.backend;

import com.google.gson.Gson;
import org.manger.backend.siteExtensions.SiteGenres;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WebLoader {
    List<SiteGenres> siteGenres;

    public void loadGenresOfSitesFromJSON() {
        ClassLoader loader = this.getClass().getClassLoader();
        File reader = new File(Objects.requireNonNull(loader.getResource("genres.json")).getPath());
        String json = null;
        try {
            json = Files.readString(Path.of(reader.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SiteGenres[] temporarySiteGenres = new Gson().fromJson(json, SiteGenres[].class);
        siteGenres = Arrays.asList(temporarySiteGenres);
    }
}
