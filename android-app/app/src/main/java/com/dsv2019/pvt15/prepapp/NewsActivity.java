package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dsv2019.pvt15.prepapp.apihandler.ApiHandler;
import com.dsv2019.pvt15.prepapp.customcomponents.NewsItemView;
import com.dsv2019.pvt15.prepapp.models.NewsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends Activity {

    private static final String NEWS_FEED_SERVICE = "http://api.krisinformation.se/v1/capmessage/?format=json";

    ApiHandler apiHandler;

    RequestQueue requestQueue;
    ArrayList<NewsItem> newsFeed;

    ProgressBar progressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //apiHandler = new ApiHandler();
        //apiHandler.setRequestQueue(this);
        //newsFeed = new ArrayList<>();
        //apiHandler.requestNewsFeed(newsFeed);

        findViewById(R.id.homeButton).setOnClickListener(l -> startActivity(new Intent(this, MainActivity.class)));

        requestQueue = Volley.newRequestQueue(this);
        newsFeed = new ArrayList<>();
        progressSpinner = findViewById(R.id.progressSpinner);

        requestNewsFeed();
    }

    public void requestNewsFeed() {

        JsonArrayRequest request = new JsonArrayRequest(NEWS_FEED_SERVICE,
                response -> {
                    for (int i = 0; i < 20; i++) {
                        try {
                            JSONObject bigObject = (JSONObject) response.get(i);
                            JSONObject innerObject = bigObject.getJSONArray("InfoData").getJSONObject(0);
                            NewsItem newsItem = new NewsItem(
                                    innerObject.getString("Headline"),
                                    innerObject.getString("Description"),
                                    innerObject.getString("Web"),
                                    bigObject.getString("Sender"),
                                    bigObject.getString("Sent"));
                            Log.d("GetNewsFeed", "NewsItem: " + newsItem);

                            if (i == 0)
                                progressSpinner.setVisibility(View.GONE);

                            newsFeed.add(newsItem);
                            addNews(newsItem);
                            Log.d("GetNewsFeed", "newsFeed size: " + newsFeed.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Throwable::printStackTrace);

        requestQueue.add(request);

        Log.d("GetNewsFeed", "List: " + newsFeed);

    }

    private void addNews(NewsItem newsItem) {
        LinearLayout layout = findViewById(R.id.newsLinearLayout);

        NewsItemView newsItemView = new NewsItemView(this, newsItem);

        newsItemView.setOnClickListener(l -> {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_news, null);
            PopupWindow pw = new PopupWindow(popupView,
                    (int) (newsItemView.getWidth() * .8),
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true);

            setPopupValues(popupView, newsItem);
            pw.showAtLocation(newsItemView, Gravity.CENTER, 0, 0);

        });

        layout.addView(newsItemView);
    }

    private void setPopupValues(View popup, NewsItem newsItem) {
        TextView title, date, desc, web;

        title = popup.findViewById(R.id.titleTextView);
        title.append(newsItem.getTitle());

        date = popup.findViewById(R.id.dateTextView);
        date.append(newsItem.getStringDate());

        desc = popup.findViewById(R.id.descriptionTextView);
        desc.append(newsItem.getDescription());

        web = popup.findViewById(R.id.webTextView);
        web.append(newsItem.getUrl());
    }
}
