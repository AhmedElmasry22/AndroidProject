package com.example.moviesapplication.DataBase;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviesapplication.Models.ModelMovies;
import com.example.moviesapplication.Models.ModelMovies.TvShowsBean;

import java.util.List;
import java.util.Observable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@androidx.room.Dao
public interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insetTv(List<TvShowsBean> list);

    @Query("Select * from tvShow_table")
    Single<List<TvShowsBean>> getAll();
}
