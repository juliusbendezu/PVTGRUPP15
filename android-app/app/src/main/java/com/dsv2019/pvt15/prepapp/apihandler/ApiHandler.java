package com.dsv2019.pvt15.prepapp.apihandler;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dsv2019.pvt15.prepapp.models.NewsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class ApiHandler {

    private static final String NEWS_FEED_SERVICE = "http://api.krisinformation.se/v1/capmessage/?format=json";

    private RequestQueue requestQueue;

    public void setRequestQueue(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getNewsFeed(ArrayList<NewsItem> newsFeed) {

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestQueue.stop();
        Log.d("GetNewsFeed", "List: " + newsFeed);

    }

}
