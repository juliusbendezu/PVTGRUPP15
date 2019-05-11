package com.dsv2019.pvt15.prepapp.models;

import org.junit.Test;


import static org.junit.Assert.*;

public class NewsItemTest {

    //jsonDate corresponds to the way we receive date info from krisinformation api.
    // The format is "yyyy-mm-ddThh:mm:ss+hh:mm"
    private NewsItem newsItem1= new NewsItem(
            "Min första nyhet", "Denna ska ligga underst",
            "https://www.krisinformation.se", "Julius", "2019-05-19T12:25:50+02:00");

    private NewsItem newsItem2= new NewsItem(
            "Min andra nyhet", "Denna ska ligga överst",
            "https://www.krisinformation.se", "Julius", "2019-05-19T13:50:30+02:00");

    @Test
    public void compareTo() {
        final int LESS_THAN = -1;
        assertTrue(newsItem1.compareTo(newsItem2) == LESS_THAN);
    }

}