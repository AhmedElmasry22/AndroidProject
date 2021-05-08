package com.example.moviesapplication.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapplication.Models.ModelMovies;
import com.example.moviesapplication.NetWork.ApiClient;
import com.example.moviesapplication.NetWork.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPopularTv {

   private ApiService apiService;
    MutableLiveData<ModelMovies> data=new MutableLiveData<>();

   public RepositoryPopularTv(){
      if (apiService==null) {
          apiService = ApiClient.getRetrofit().create(ApiService.class);
      }
    }
    public LiveData<ModelMovies> getpopularTv(int page){

        apiService.getMostPopularTvShow(page).enqueue(new Callback<ModelMovies>() {
            @Override
            public void onResponse(Call<ModelMovies> call, Response<ModelMovies> response) {
                 data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMovies> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;

    }
}
