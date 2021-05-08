package com.example.retrofitwithmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.retrofitwithmvvm.Adapters.TvShowAdapter;
import com.example.retrofitwithmvvm.Models.TVShows;
import com.example.retrofitwithmvvm.ViewModels.MostPopularTvViewModel;
import com.example.retrofitwithmvvm.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MostPopularTvViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TVShows> tvShows = new ArrayList<>();
    private TvShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);

        DoIntialize();
    }

    private void DoIntialize()
    {
        activityMainBinding.tvShowRecyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this).get(MostPopularTvViewModel.class);
        tvShowAdapter = new TvShowAdapter(tvShows);
        activityMainBinding.tvShowRecyclerView.setAdapter(tvShowAdapter);
        getMostPopularTvShows();

    }

    private void getMostPopularTvShows()
    {
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTvShows(0).observe(this , mostPopularTVShowsResponse ->
        {
            activityMainBinding.setIsLoading(false);
            if (mostPopularTVShowsResponse != null )
            {
                if(mostPopularTVShowsResponse.getTvShows() != null)
                {
                    tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                    tvShowAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}