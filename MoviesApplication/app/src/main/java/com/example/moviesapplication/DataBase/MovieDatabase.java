package com.example.moviesapplication.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviesapplication.Models.ModelMovies;

@Database(entities = ModelMovies.TvShowsBean.class,version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase instance;
    public abstract Dao MovieDao();

    public static synchronized MovieDatabase getInstance(Context context) {
        if (instance==null){
            instance=Room.databaseBuilder(context.getApplicationContext()
                    , MovieDatabase.class,"Movie_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;

    }

}
