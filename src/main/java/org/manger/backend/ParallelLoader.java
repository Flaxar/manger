package org.manger.backend;

import com.google.gson.Gson;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.manger.backend.siteExtensions.MangaInfo;
import org.manger.frontend.Chapter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParallelLoader extends Thread{
    String URL;
    MangaInfo manga;
    ImageView mangaCover;
    ListView<String> chapterList;

    public ParallelLoader(MangaInfo manga, ListView<String> chapterList, ImageView mangaCover) {
        this.chapterList = chapterList;
        this.manga = manga;
        URL = "https://cover.nep.li/cover/" + manga.getHeadURL() + ".jpg";
        this.mangaCover = mangaCover;
    }

    public void run() {
        chapterList.getItems().clear();
        for(Chapter chapter : fetchChapters(manga)) {
            chapterList.getItems().add(chapter.getType() + " " + chapter.getChapterNumber());
        }

        Image coverImage = new Image(URL);
        mangaCover.setImage(coverImage);
        List<Chapter> chapters = fetchChapters(manga);
    }

    private List<Chapter> fetchChapters(MangaInfo manga) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://mangasee123.com/manga/" + manga.getHeadURL()).maxBodySize(5000000).get();
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
            chapter.parseInfo();
        }

        return Arrays.asList(chapters);
    }


}
