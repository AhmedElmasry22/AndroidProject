package com.example.retrofitwithmvvm.Interfacees;

import com.example.retrofitwithmvvm.Response.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServece {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTvShows(@Query("page") int page);

}
