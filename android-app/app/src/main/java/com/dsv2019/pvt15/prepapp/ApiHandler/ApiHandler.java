package com.dsv2019.pvt15.prepapp.ApiHandler;

import com.dsv2019.pvt15.prepapp.models.NewsItem;

import java.util.TreeSet;

public class ApiHandler {

    private static final String NEWS_FEED_SERVICE = "http://api.krisinformation.se/v1/capmessage/?format=json";

    private static final String JSON_FOR_TESTING = "{\n" +
            "    \"Identifier\": \"11643\",\n" +
            "    \"Sender\": \"https://www.krisinformation.se/\",\n" +
            "    \"Sent\": \"2019-04-29T10:50:59+02:00\",\n" +
            "    \"Published\": \"2019-04-29T10:50:59+02:00\",\n" +
            "    \"Status\": \"Actual\",\n" +
            "    \"MsgType\": \"Alert\",\n" +
            "    \"Scope\": \"Public\",\n" +
            "    \"InfoData\": [\n" +
            "      {\n" +
            "        \"Language\": \"sv-SE\",\n" +
            "        \"Category\": \"Other\",\n" +
            "        \"Event\": \"News\",\n" +
            "        \"Urgency\": \"Unknown\",\n" +
            "        \"Severity\": \"Unknown\",\n" +
            "        \"Certainty\": \"Unknown\",\n" +
            "        \"Headline\": \"Om Valborg och eldningsförbud\",\n" +
            "        \"Description\": \"Det torra och varma vädret har gjort att brandrisken i skog och mark är stor i delar av Sverige. I många kommuner råder eldningsförbud.\",\n" +
            "        \"SenderName\": \"\",\n" +
            "        \"Web\": \"https://www.krisinformation.se/nyheter/2019/april/om-valborg-och-eldningsforbud/\",\n" +
            "        \"Area\": [\n" +
            "          {\n" +
            "            \"AreaDesc\": \"Sverige\",\n" +
            "            \"Type\": \"Sovereign country\",\n" +
            "            \"Polygon\": [\n" +
            "              {\n" +
            "                \"Polygons\": \"16.596265846848,62.8114849680804\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Resources\": null,\n" +
            "    \"Active\": true,\n" +
            "    \"IsNewVma\": false\n" +
            "  }";

    public TreeSet<NewsItem> getNewsFeedAsItems(){
        TreeSet<NewsItem> newsFeed = new TreeSet<>();


        return newsFeed;
    }

}
