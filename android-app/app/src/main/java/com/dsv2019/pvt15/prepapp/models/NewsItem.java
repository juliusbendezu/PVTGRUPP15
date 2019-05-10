package com.dsv2019.pvt15.prepapp.models;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class NewsItem implements Comparable<NewsItem> {

    private String title;
    private String description;
    private String url;
    private String author;
    private Date date;

    public NewsItem(String title, String description, String url, String author, String jsonDate) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.author = author;

        Log.d("NewsItem", "jsonDate: " + jsonDate);
        String correctDate;
        correctDate = jsonDate.replace(jsonDate.charAt(10), ' ');
        correctDate = correctDate.substring(0, 19);
        Log.d("NewsItem", "correctDate: " + correctDate);

        SimpleDateFormat formatter = new SimpleDateFormat();

        try {
            date = formatter.parse(correctDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return title.equals(newsItem.title) &&
                description.equals(newsItem.description) &&
                url.equals(newsItem.url) &&
                author.equals(newsItem.author) &&
                date.equals(newsItem.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, url, author, date);
    }

    @Override
    public int compareTo(NewsItem o) {
        return date.compareTo(o.date);
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                '}';
    }

}
