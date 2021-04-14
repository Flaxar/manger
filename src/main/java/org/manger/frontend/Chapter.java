package org.manger.frontend;

public class Chapter {
    String Chapter;
    String Type;
    String Date;
    String ChapterName;
    String URL;

    public Chapter(String chapter, String Type, String Date, String ChapterName) {
        this.Chapter = chapter;
        this.Type = Type;
        this.Date = Date;
        this.ChapterName = ChapterName;
    }

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String chapter) {
        this.Chapter = chapter;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String ChapterName) {
        this.ChapterName = ChapterName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
