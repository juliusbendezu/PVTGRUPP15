package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dsv2019.pvt15.prepapp.ApiHandler.ApiHandler;
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
        //apiHandler.getNewsFeed(newsFeed);

        requestQueue = Volley.newRequestQueue(this);
        newsFeed = new ArrayList<>();
        getNewsFeed();
    }

    public void getNewsFeed() {

        JsonArrayRequest request = new JsonArrayRequest(NEWS_FEED_SERVICE,
                response -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            JSONObject bigObject = (JSONObject) response.get(i);
                            JSONObject innerObject = bigObject.getJSONArray("InfoData").getJSONObject(0);
                            NewsItem n = new NewsItem(
                                    innerObject.getString("Headline"),
                                    innerObject.getString("Description"),
                                    innerObject.getString("Web"),
                                    bigObject.getString("Sender"),
                                    bigObject.getString("Sent"));
                            Log.d("GetNewsFeed", "NewsItem: " + n);
                            newsFeed.add(n);
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

}
