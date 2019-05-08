package com.dsv2019.pvt15.prepapp.models;

import java.text.DateFormat;

public class NewsItem {

    private String title;
    private String description;
    private String url;
    private String author;
    private String date; //Lär kanske behöva ändras till DateFormat etc men det får krisinfo-api bestämma

    public NewsItem(String title, String description, String url, String author, String date) {
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

    public String getDate() {
        return date;
    }
}
