package com.example.moviesapplication.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviesapplication.Models.ModelMovieDetail;
import com.example.moviesapplication.NetWork.ApiClient;
import com.example.moviesapplication.NetWork.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryShowDetail {
    private ApiService apiService;

    MutableLiveData<ModelMovieDetail> data =new MutableLiveData<>();
    public RepositoryShowDetail(){
        apiService= ApiClient.getRetrofit().create(ApiService.class);
    }

    public MutableLiveData<ModelMovieDetail> getDetail(String tvshow){
        apiService.getMovieDetail(tvshow).enqueue(new Callback<ModelMovieDetail>() {
            @Override
            public void onResponse(Call<ModelMovieDetail> call, Response<ModelMovieDetail> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMovieDetail> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }



}
