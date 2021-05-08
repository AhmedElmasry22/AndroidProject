package com.example.retrofitwithmvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofitwithmvvm.Repository.ReposotryMostPopularTvShow;
import com.example.retrofitwithmvvm.Response.TVShowResponse;

public class MostPopularTvViewModel extends ViewModel {

    private ReposotryMostPopularTvShow reposotryMostPopularTvShow ;

    public MostPopularTvViewModel()
    {
        reposotryMostPopularTvShow = new ReposotryMostPopularTvShow();
    }

    public LiveData<TVShowResponse> getMostPopularTvShows(int page)
    {
        return reposotryMostPopularTvShow.getMostPopularTvShows(page);
    }

}
