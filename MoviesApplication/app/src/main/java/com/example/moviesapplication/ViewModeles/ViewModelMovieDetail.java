package com.example.moviesapplication.ViewModeles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesapplication.Models.ModelMovieDetail;
import com.example.moviesapplication.Repositories.RepositoryShowDetail;

public class ViewModelMovieDetail extends ViewModel {
    RepositoryShowDetail repositoryShowDetail;

    public ViewModelMovieDetail(){
        if (repositoryShowDetail==null){
            repositoryShowDetail=new RepositoryShowDetail();
        }
    }
    public LiveData<ModelMovieDetail> getMovieDetail(String tvShow){
        return repositoryShowDetail.getDetail(tvShow) ;
    }

}
