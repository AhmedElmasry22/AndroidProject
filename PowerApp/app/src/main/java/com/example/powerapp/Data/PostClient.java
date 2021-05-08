package com.example.powerapp.Data;

import com.example.powerapp.Models.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostClient {

    private static final String Base_URL = "https://jsonplaceholder.typicode.com/";
    private  PostInterFace postInterFace;
    private static PostClient INSTANCE;

    public PostClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postInterFace = retrofit.create(PostInterFace.class);
    }

    public static PostClient getINSTANCE() {

        if (INSTANCE == null)
        {
            INSTANCE = new PostClient();
        }

        return INSTANCE;
    }

    public Call<List<PostModel>> getPosts(){
        return postInterFace.getposts();
    }

}
