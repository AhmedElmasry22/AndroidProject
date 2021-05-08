package com.example.foodordering.retrofit;

import com.example.foodordering.Models.FoodData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterface {


    @GET("fooddata.json")
    Call<List<FoodData>> getAllData();

}
