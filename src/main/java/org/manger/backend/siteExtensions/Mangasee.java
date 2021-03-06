package org.manger.backend.siteExtensions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.manger.frontend.Chapter;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mangasee implements MangaWebsite {

    String mangaseeURL = "https://mangasee123.com/";

    @Override
    public List<MangaInfo> loadListOfAllMangas() throws IOException, InterruptedException {
        Document doc = Jsoup.connect(mangaseeURL + "search/").userAgent("Mozilla/5.0").maxBodySize(5000000).get();
        Elements scriptElements = doc.getElementsByTag("script");
        String unparsedScript = scriptElements.html();

        // Regex
        Pattern pattern = Pattern.compile("vm.Directory = ([^\\n]*);");
        Matcher matcher = pattern.matcher(unparsedScript);
        matcher.find();
        String unparsedJson = matcher.group(0);

        unparsedJson = unparsedJson.replace("vm.Directory = ", "");
        unparsedJson = unparsedJson.substring(0, unparsedJson.length() - 1);
        Gson gson = new Gson();
        MangaInfo[] mangas = gson.fromJson(unparsedJson, MangaInfo[].class);

        return Arrays.asList(mangas);
    }

    @Override
    public List<String> loadGenres() {
        Class<Mangasee> loader = Mangasee.class;
        InputStream i = loader.getResourceAsStream("/siteGenres/Mangasee.json");
        assert i != null;
        BufferedReader r = new BufferedReader(new InputStreamReader(i));
        String json = null;
        try {
            json = r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public void loadMangaCover(ImageView imageView, MangaInfo manga) {
        class ParallelImageLoader extends Thread {
            String URL;
            ImageView mangaCover;

            public ParallelImageLoader(String URL, ImageView imageView) {
                this.URL = URL;
                mangaCover = imageView;
            }

            public void run() {
                Image coverImage = new Image(URL);
                mangaCover.setImage(coverImage);
            }
        }
        String URL = "https://cover.nep.li/cover/" + manga.getHeadURL() + ".jpg";
        ParallelImageLoader loader = new ParallelImageLoader(URL, imageView);
        loader.start();
    }

    @Override
    public List<Chapter> getMangaChapters(MangaInfo manga) {
        Document doc = null;
        try {
            doc = Jsoup.connect(mangaseeURL + "manga/" + manga.getHeadURL())
                    .userAgent("Mozilla/5.0")
                    .maxBodySize(5000000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements scriptElements = doc.getElementsByTag("script");
        String unparsedScript = scriptElements.html();

        Pattern pattern = Pattern.compile("vm.Chapters = ([^\\n]*);");
        Matcher matcher = pattern.matcher(unparsedScript);
        matcher.find();
        String unparsedJson = matcher.group(0);

        unparsedJson = unparsedJson.replace("vm.Chapters = ", "");
        unparsedJson = unparsedJson.substring(0, unparsedJson.length() - 1);
        Gson gson = new Gson();
        Chapter[] chapters = gson.fromJson(unparsedJson, Chapter[].class);

        for(Chapter chapter : chapters) {
            chapter.parseInfo(manga.getHeadURL());
        }

        return Arrays.asList(chapters);
    }

    @Override
    public void openMangaInBrowser(MangaInfo manga) {
        try {
            Desktop.getDesktop().browse(new URL(mangaseeURL + "manga/" + manga.getHeadURL()).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
