package com.example.moviesapplication;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapplication.Adapters.AdapterMostPopularTv;
import com.example.moviesapplication.Models.ModelMovies;
import com.example.moviesapplication.ViewModeles.MostPopularTVShowsViewModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MostPopularTVShowsViewModel viewModel;
    private RecyclerView recyclerView_movies;
    private AdapterMostPopularTv mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<ModelMovies.TvShowsBean> list = new ArrayList<>();
    int current_page = 1;
    int total_pages = 1;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView_movies = findViewById(R.id.recycle_popular_movies);
        progressBar = findViewById(R.id.progress_movie_popular);

        recyclerView_movies.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterMostPopularTv(MainActivity.this, list);
        recyclerView_movies.setLayoutManager(mLayoutManager);
        recyclerView_movies.setAdapter(mAdapter);


        viewModel = ViewModelProviders.of(this).get(MostPopularTVShowsViewModel.class);
        getPopularMovie();
        recyclerView_movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (current_page <= total_pages) {
                    current_page++;
                    getPopularMovie();

                }
            }
        });

        mAdapter.setOnItemClickListner(new AdapterMostPopularTv.OnItemClickListner() {
            @Override
            public void OnItemCLick(int postion) {
                ModelMovies.TvShowsBean item = list.get(postion);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);

                intent.putExtra("id", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("startData", item.getStart_date());
                intent.putExtra("status", item.getStatus());
                intent.putExtra("country", item.getCountry());
                intent.putExtra("network", item.getNetwork());
                Toast.makeText(MainActivity.this, "" + item.getName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        });


    }

    private void getPopularMovie() {
        viewModel.getMostPopularTv(current_page).observe(this, modelMovies -> {
            if (modelMovies != null) {
                int oldCount = list.size();
                list.addAll(modelMovies.getTv_shows());

                recyclerView_movies.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                total_pages = modelMovies.getPages();
                mAdapter.notifyItemRangeInserted(oldCount, list.size());

            }

        });
        viewModel.addtvShow(list).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

                Toast.makeText(MainActivity.this, "xx", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }
        });

        if(isNetworkAvailable()==false){
            viewModel.getTv().subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<ModelMovies.TvShowsBean>>() {
                @Override
                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@io.reactivex.annotations.NonNull List<ModelMovies.TvShowsBean> list) {
                        list.addAll(list);
                }

                @Override
                public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_database:
                Intent intent=new Intent(MainActivity.this,TvShowDatabaseActivity.class);
                startActivity(intent);
                break;


        }
        return false;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
