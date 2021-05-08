package com.example.news.Retrofit;

import com.example.news.Models.Model_News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    // Start On Recycle View On Main Activity
    @GET("top-headlines")
    Call<Model_News> getNews(

           @Query("country") String country,
           @Query("apiKey") String apiKey

    );
    // End ***********************************

    // Starct Seraching On Main Activity
    @GET("everything")
    Call<Model_News> getNewsSearch(

            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
    // ***********************************************


}
