package com.dsv2019.pvt15.prepapp;

import com.dsv2019.pvt15.prepapp.tipsrelated.BaseAPIService;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String ROOT_URL = "https://pvt15.herokuapp.com/";

    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static BaseAPIService getApiService(){
        return getRetrofitInstance().create(BaseAPIService.class);
    }
}
