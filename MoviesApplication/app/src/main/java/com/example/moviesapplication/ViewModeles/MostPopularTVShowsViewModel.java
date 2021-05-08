package com.example.moviesapplication.ViewModeles;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesapplication.DataBase.MovieDatabase;
import com.example.moviesapplication.Models.ModelMovies;
import com.example.moviesapplication.Repositories.RepositoryPopularTv;

import java.util.List;
import java.util.Observable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class MostPopularTVShowsViewModel extends AndroidViewModel {

    public RepositoryPopularTv repositoryPopularTv;
    MovieDatabase movieDatabase;

    public MostPopularTVShowsViewModel(Application application){
        super(application);
        if (repositoryPopularTv==null) {
            repositoryPopularTv = new RepositoryPopularTv();
        }
        movieDatabase=MovieDatabase.getInstance(application);
    }

    public LiveData<ModelMovies> getMostPopularTv(int page){
        return repositoryPopularTv.getpopularTv(page);

    }

    public Completable addtvShow(List<ModelMovies.TvShowsBean> tvShowsBean){
        return movieDatabase.MovieDao().insetTv(tvShowsBean);

    }
    public Single<List<ModelMovies.TvShowsBean>> getTv(){
        return movieDatabase.MovieDao().getAll();
    }

}
