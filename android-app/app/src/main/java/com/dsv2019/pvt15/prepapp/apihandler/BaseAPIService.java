package com.dsv2019.pvt15.prepapp.apihandler;

import com.dsv2019.pvt15.prepapp.models.PantryItem;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

//    @FormUrlEncoded
//    @POST("tips/add")
//    Call<Tip> addTip(
//            @Field("title") String title,
//            @Field("warmth") boolean warmth,
//            @Field("water") boolean water,
//            @Field("shelter") boolean shelter,
//            @Field("food") boolean food,
//            @Field("health") boolean health,
//            @Field("security") boolean security,
//            @Field("storage") boolean storage,
//            @Field("other") boolean other,
//            @Field("description") String description,
//            @Field("likes") int likes,
//            @Field("creator") String creator
//    );

    @POST("tips/add")
    Call<Tip> addTip(@Body Tip tip);

}