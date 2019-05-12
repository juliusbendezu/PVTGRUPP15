package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //apiHandler = new ApiHandler();
        //apiHandler.setRequestQueue(this);
        //newsFeed = new ArrayList<>();
        //apiHandler.requestNewsFeed(newsFeed);

        requestQueue = Volley.newRequestQueue(this);
        newsFeed = new ArrayList<>();
        requestNewsFeed();
    }

    public void requestNewsFeed() {

        JsonArrayRequest request = new JsonArrayRequest(NEWS_FEED_SERVICE,
                response -> {
                    for (int i = 0; i < 10; i++) {
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
            if (newsItemView.getBackground() == null)
                newsItemView.setBackgroundColor(Color.CYAN);
            else
                newsItemView.setBackground(null);
        });

        layout.addView(newsItemView);
    }

}
