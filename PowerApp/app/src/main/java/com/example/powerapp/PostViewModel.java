package com.example.powerapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.powerapp.Data.PostClient;
import com.example.powerapp.Models.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {


    MutableLiveData<List<PostModel>> postmutablelivedata = new MutableLiveData<>();
    MutableLiveData<String> posts = new MutableLiveData<>();


    public void getposts(){

        PostClient.getINSTANCE().getPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                postmutablelivedata.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                posts.setValue("errr");


            }
        });

    }

}
