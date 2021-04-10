package org.manger.backend.siteExtensions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mangasee implements MangaWebsite {
    String mangaseeURL = "https://mangasee123.com/_search.php";

    @Override
    public List<MangaInfo> loadListOfAllMangas() throws IOException, InterruptedException {
        Document doc = Jsoup.connect("https://mangasee123.com/search/").maxBodySize(5000000).get();
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
    public List<MangaInfo> getMangasByGenre() {
        return null;
    }
}
