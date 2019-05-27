package com.dsv2019.pvt15.prepapp.apihandler;

import com.dsv2019.pvt15.prepapp.models.PantryItem;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;
import com.facebook.AccessToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface BaseAPIService {

    String mockUser = "Julius";
    String userId = AccessToken.getCurrentAccessToken().toString();

    /*
     * General
     */

    @GET("api/wakeup")
    Call<String> wakeServer();


    /*
     * Tips
     */

    @GET("tips/all")
    Call<List<Tip>> getTips();

    @GET("tips/")
    Call<String> getStringTips();

    @GET("helloThere/General")
    Call<String> getHelloString();

    @POST("tips/add")
    Call<Tip> addTip(@Body Tip tip);


    /*
     * PANTRY
     */

    @GET("pantry/" + mockUser)
    Call<List<PantryItem>> getPantry();

    @POST("pantry/add")
    Call<PantryItem> addPantryItem(@Body PantryItem item);

    @PUT("pantry/update")
    Call<PantryItem> updatePantryItem(@Body PantryItem item);
}