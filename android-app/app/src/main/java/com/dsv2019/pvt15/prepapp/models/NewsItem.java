package com.dsv2019.pvt15.prepapp.models;


import java.util.Date;

public class NewsItem implements Comparable<NewsItem> {

    private String title;
    private String description;
    private String url;
    private String author;
    private Date date;

    public NewsItem(String title, String description, String url, String author, Date date) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.author = author;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(NewsItem o) {
        return date.compareTo(o.date);
    }

}
