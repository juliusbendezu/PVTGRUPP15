package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.os.Bundle;

import com.dsv2019.pvt15.prepapp.ApiHandler.ApiHandler;
import com.dsv2019.pvt15.prepapp.models.NewsItem;

import java.util.TreeSet;

public class NewsActivity extends Activity {

    ApiHandler apiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        apiHandler = new ApiHandler();

        TreeSet<NewsItem> news = apiHandler.getNewsFeedAsItems();

    }
}
