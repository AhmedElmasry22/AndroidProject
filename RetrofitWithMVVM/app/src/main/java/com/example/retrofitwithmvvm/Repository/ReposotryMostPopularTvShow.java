package com.example.retrofitwithmvvm.Repository;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.retrofitwithmvvm.Interfacees.ApiServece;
import com.example.retrofitwithmvvm.NetWork.ApiClient;
import com.example.retrofitwithmvvm.Response.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposotryMostPopularTvShow
{

    private ApiServece apiServece ;

    public ReposotryMostPopularTvShow()
    {
        apiServece = ApiClient.getRetrofit().create(ApiServece.class);
    }

    public LiveData<TVShowResponse> getMostPopularTvShows(int page)
    {
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiServece.getMostPopularTvShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call,@NonNull Response<TVShowResponse> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                 data.setValue(null);
            }
        });

        return data ;
    }

}
