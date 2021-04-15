package org.manger.backend;

import org.jsoup.select.Evaluator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.JDBCType.NULL;

public class DatabaseController {
    Connection connection;
    Statement statement;

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
                "\t\"Index\"\tINTEGER NOT NULL,\n" +
                "\t\"Point\"\tINTEGER NOT NULL,\n" +
                "\t\"Type\"\tTEXT NOT NULL,\n" +
                "\t\"Date\"\tTEXT NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\t\"URL\"\tTEXT NOT NULL\n" +
                ")";
        statement.execute(tableSQL);
    }
}
