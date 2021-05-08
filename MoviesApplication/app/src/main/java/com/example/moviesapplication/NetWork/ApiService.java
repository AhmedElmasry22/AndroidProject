package com.example.moviesapplication.NetWork;

import com.example.moviesapplication.Models.ModelMovieDetail;
import com.example.moviesapplication.Models.ModelMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<ModelMovies> getMostPopularTvShow(@Query("page") int page);

    @GET("show-details")
    Call<ModelMovieDetail> getMovieDetail(@Query("q") String tvShowId);
}
