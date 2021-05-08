package com.example.powerapp.Data;

import com.example.powerapp.Models.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterFace {

    @GET("posts")
    public Call<List<PostModel>> getposts(



    );
}
