package com.example.moviesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.moviesapplication.Adapters.AdapterMostPopularTv;
import com.example.moviesapplication.DataBase.MovieDatabase;
import com.example.moviesapplication.Models.ModelMovies;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowDatabaseActivity extends AppCompatActivity {
    private AdapterMostPopularTv mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<ModelMovies.TvShowsBean> list2 = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_database);

        recyclerView=findViewById(R.id.recycle_database);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);


        final MovieDatabase postsDatabase = MovieDatabase.getInstance(this);
        postsDatabase.MovieDao().getAll().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<ModelMovies.TvShowsBean>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<ModelMovies.TvShowsBean> list) {
                mAdapter = new AdapterMostPopularTv(TvShowDatabaseActivity.this, list);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }
}