package com.example.moviesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moviesapplication.Adapters.AdapterImageDetail;
import com.example.moviesapplication.Adapters.AdapterMostPopularTv;
import com.example.moviesapplication.Adapters.ViewPagerAdapter;
import com.example.moviesapplication.Models.ModelMovieDetail;
import com.example.moviesapplication.ViewModeles.ViewModelMovieDetail;

import java.util.ArrayList;
import java.util.List;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MovieDetailActivity extends AppCompatActivity {
    ViewModelMovieDetail viewModel;
    private RecyclerView recyclerView;

    String[] list_images = {};
    AdapterImageDetail adapterImageDetail;
    private RecyclerView.LayoutManager mLayoutManager;
    int id;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        viewModel = ViewModelProviders.of(this).get(ViewModelMovieDetail.class);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

      
        ViewPager viewPager = findViewById(R.id.view_pager);
        if (adapter!=null) {
            adapter = new ViewPagerAdapter(this, list_images);
            viewPager.setAdapter(adapter);
        }
       /*
        if(adapterImageDetail!=null) {
            adapterImageDetail = new AdapterImageDetail(MovieDetailActivity.this, list_images);
        }
        recyclerView.setAdapter(adapterImageDetail);


        */

        viewModel.getMovieDetail(String.valueOf(id)).observe(this, new Observer<ModelMovieDetail>() {
            @Override
            public void onChanged(ModelMovieDetail modelMovieDetail) {
                list_images = modelMovieDetail.getModelMovieDetail().getPictures();

                if(adapter==null){
                    adapter = new ViewPagerAdapter(MovieDetailActivity.this, list_images);
                    viewPager.setAdapter(adapter);

                }

                /*
                      if (adapterImageDetail==null) {
                          adapterImageDetail = new AdapterImageDetail(MovieDetailActivity.this, list_images);
                          recyclerView.setAdapter(adapterImageDetail);
                      }


 */
                Toast.makeText(MovieDetailActivity.this, "" + modelMovieDetail.getModelMovieDetail().getDescription(), Toast.LENGTH_SHORT).show();

            }



        });



    }









}