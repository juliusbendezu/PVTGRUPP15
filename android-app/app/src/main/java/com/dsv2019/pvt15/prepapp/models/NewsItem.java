package com.dsv2019.pvt15.prepapp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class NewsItem implements Comparable<NewsItem> {

    private String title;
    private String description;
    private String url;
    private String sender;
    private Date date;
    private String stringDate; //As backup if parsing fails + used for toString

    public NewsItem(String title, String description, String url, String author, String jsonDate) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.sender = author;

        String correctDate;
        correctDate = jsonDate.replace(jsonDate.charAt(10), ' ');
        correctDate = correctDate.substring(0, 19);

        //Removes seconds, they are only interesting when comparing dates.
        stringDate = correctDate.substring(0,16);

        SimpleDateFormat formatter = new SimpleDateFormat();
        try {
            date = formatter.parse(correctDate);
        } catch (ParseException e) {
            date = null;
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

    public String getSender() {
        return sender;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        return stringDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return title.equals(newsItem.title) &&
                description.equals(newsItem.description) &&
                url.equals(newsItem.url) &&
                sender.equals(newsItem.sender) &&
                date.equals(newsItem.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, url, sender, date);
    }

    @Override
    public int compareTo(NewsItem o) {
        return date.compareTo(o.date);
    }

    public String getSummary(){
        return String.format("%s\n%s", stringDate, title);
    }

}
