package com.example.projectuts_00000017639;

public class books {
    private String Asin;
    private String Group;
    private String Format;
    private String Title;
    private String Author;
    private String Publisher;
    private int Favorites;

    public books() {}

    public books(String asin, String group, String format, String title, String author, String publisher, int favorites) {
        Asin = asin;
        Group = group;
        Format = format;
        Title = title;
        Author = author;
        Publisher = publisher;
        Favorites = favorites;
    }

    public String getAsin() {
        return Asin;
    }

    public void setAsin(String asin) {
        Asin = asin;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public int getFavorites() {
        return Favorites;
    }

    public void setFavorites(int favorites) {
        Favorites = favorites;
    }
}
