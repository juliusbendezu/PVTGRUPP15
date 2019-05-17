package com.dsv2019.pvt15.prepapp.tipsrelated;

import com.dsv2019.pvt15.prepapp.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BaseAPIService {

    @GET("tips/all")
    Call<List<Tip>> getTips();

    @GET("tips/")
    Call<String> getStringTips();

    @GET("helloThere/General")
    Call<String> getHelloString();

    @GET("posts")
    Call<List<Post>> getPosts();



}