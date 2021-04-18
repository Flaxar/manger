package org.manger.backend;

import org.jsoup.select.Evaluator;
import org.manger.backend.siteExtensions.MangaInfo;
import org.manger.frontend.Chapter;

import java.io.File;
import java.sql.*;

import static java.sql.JDBCType.NULL;

public class DatabaseController {
    private Connection connection;
    private Statement statement;
    WebLoader loader;

    public DatabaseController() {
        String url = "jdbc:sqlite:database.db";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            createTables();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setLoader(WebLoader loader) {
        this.loader = loader;
    }

    private void createTables() throws SQLException {
        String tableSQL = "CREATE TABLE IF NOT EXISTS \"MANGAS\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"Title\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"URLHeader\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"Type\"\tTEXT NOT NULL,\n" +
                "\t\"Site\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ")";
        statement.execute(tableSQL);

        tableSQL = "CREATE TABLE IF NOT EXISTS \"GENRES\" (\n" +
                "\t\"MangaID\"\tINTEGER NOT NULL,\n" +
                "\t\"Genre\"\tTEXT NOT NULL\n" +
                ")";
        statement.execute(tableSQL);

        tableSQL = "CREATE TABLE IF NOT EXISTS \"CHAPTERS\" (\n" +
                "\t\"MangaID\"\tINTEGER NOT NULL,\n" +
                "\t\"Number\"\tINTEGER NOT NULL,\n" +
                "\t\"ChapIndex\"\tINTEGER NOT NULL,\n" +
                "\t\"Point\"\tINTEGER NOT NULL,\n" +
                "\t\"Type\"\tTEXT NOT NULL,\n" +
                "\t\"Date\"\tTEXT NOT NULL,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"URL\"\tTEXT NOT NULL\n" +
                ")";
        statement.execute(tableSQL);
    }

    public void addMangaToLibrary(MangaInfo manga) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO MANGAS (Title, URLHeader, Type, Site) VALUES (?, ?, ?, ?)"
            );
            stmt.setString(1, manga.getTitle());
            stmt.setString(2, manga.getHeadURL());
            stmt.setString(3, manga.getType());
            stmt.setString(4, loader.getCurrentSiteName());
            stmt.executeUpdate();

            stmt = connection.prepareStatement("SELECT ID FROM MANGAS WHERE Title = ?");
            stmt.setString(1, manga.getTitle());
            ResultSet resultSet = stmt.executeQuery();
            int mangaId = resultSet.getInt("ID");

            for(String genre : manga.getGenres()) {
                stmt = connection.prepareStatement("INSERT INTO GENRES (MangaID, Genre) VALUES (?, ?)");
                stmt.setInt(1, mangaId);
                stmt.setString(2, genre);
                stmt.executeUpdate();
            }

            for(Chapter chapter : loader.getLastLoadedChapters()) {
                stmt = connection.prepareStatement("INSERT INTO CHAPTERS (MangaID, Number, ChapIndex, Point, Type, Date, Name, URL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, mangaId);
                stmt.setInt(2, chapter.getIntChapterNumber());
                stmt.setInt(3, chapter.getIndex());
                stmt.setInt(4, chapter.getPoint());
                stmt.setString(5, chapter.getType());
                stmt.setString(6, chapter.getDate());
                stmt.setString(7, chapter.getChapterName());
                stmt.setString(8, chapter.getURL());
                stmt.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
