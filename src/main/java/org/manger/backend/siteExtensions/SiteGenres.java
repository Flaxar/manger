package org.manger.backend.siteExtensions;

import java.util.List;

public class SiteGenres {
    String siteName;
    List<String> genres;

    public SiteGenres(String siteName, List<String> genres) {
        this.siteName = siteName;
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getSiteName() {
        return siteName;
    }
}
