package org.manger.backend.siteExtensions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class Mangasee implements MangaWebsite {
    String mangaseeURL = "https://mangasee123.com/_search.php";

    @Override
    public List<MangaInfo> loadListOfAllMangas() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(mangaseeURL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        MangaInfo[] mangas = gson.fromJson(response.body(), MangaInfo[].class);

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
