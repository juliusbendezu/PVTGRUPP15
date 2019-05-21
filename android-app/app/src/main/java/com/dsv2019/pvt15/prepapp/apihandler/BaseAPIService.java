package com.dsv2019.pvt15.prepapp.apihandler;

import com.dsv2019.pvt15.prepapp.models.PantryItem;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface BaseAPIService {

    String mockUsername = "Julius";

    @GET("tips/all")
    Call<List<Tip>> getTips();

    @GET("tips/")
    Call<String> getStringTips();

    @GET("helloThere/General")
    Call<String> getHelloString();

    @GET("pantry/" + mockUsername)
    Call<List<PantryItem>> getPantry();

    @FormUrlEncoded
    @POST("tips/add")
    Call<Tip> addTip(@Field("title") String title,
                        @Field("description") String description,
                        @Field("warmth") boolean isWarmth,
                       @Field("water") boolean isWater,
                       @Field("shelter") boolean isShelter,
                       @Field("food") boolean isFood,
                       @Field("health") boolean isHealth,
                       @Field("security") boolean isSecurity,
                       @Field("storage") boolean isStorage,
                       @Field("other") boolean isOther,
                       @Field("likes") int likes,
                       @Field("creator") String creator
    );


}