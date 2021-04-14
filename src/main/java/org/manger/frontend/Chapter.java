package org.manger.frontend;

public class Chapter {
    String Chapter;
    String Type;
    String Date;
    String ChapterName;
    String URL;

    int index;
    int chapterNumber;
    int point = 0;

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

    public String getChapterNumber() {
        if(point != 0) {
            return chapterNumber + "." + point;
        } else {
            return Integer.toString(chapterNumber);
        }
    }

    public void parseInfo() {
        index = Integer.parseInt(Chapter.substring(0, 1));
        chapterNumber = Integer.parseInt(Chapter.substring(1, 5));
        point = Integer.parseInt(Chapter.substring(5));
    }
}
