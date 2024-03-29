package com.dsv2019.pvt15.prepapp.apihandler;

import com.dsv2019.pvt15.prepapp.models.PantryItem;
import com.dsv2019.pvt15.prepapp.tipsrelated.Tip;
import com.facebook.AccessToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("tips/{id}")
    Call<Tip> getTip(@Path("id") int id);

    @GET("tips/by/{userid}")
    Call<List<Tip>> getTipsByUser(@Path("userid") String userId);

    @DELETE("tips/delete/{id}")
    Call<Tip> deleteTip(@Path("id")int id);

    @PUT("tips/update")
    Call<Tip> updateTip(@Body Tip tip);


    /*
     * PANTRY
     */

    @GET("pantry/" + mockUser)
    Call<List<PantryItem>> getPantry();

    @POST("pantry/add")
    Call<PantryItem> addPantryItem(@Body PantryItem item);

    @PUT("pantry/update")
    Call<PantryItem> updatePantryItem(@Body PantryItem item);

    @DELETE("pantry/delete/{id}")
    Call<PantryItem> deletePantryItem(@Path("id") int id);
}