package com.example.retrofitwithmvvm.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitwithmvvm.Models.TVShows;
import com.example.retrofitwithmvvm.R;
import com.example.retrofitwithmvvm.databinding.ItemContainerTvShowBinding;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TVShowViewHolder>
{
    private List<TVShows> tvShows;
    private LayoutInflater layoutInflater;

    public TvShowAdapter(List<TVShows> tvShows) {
        this.tvShows = tvShows;
    }




    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(layoutInflater
        , R.layout.item_container_tv_show , parent , false);

        return new TVShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {

        holder.bindTvShow(tvShows.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    static class TVShowViewHolder extends RecyclerView.ViewHolder
    {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TVShowViewHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTvShow(TVShows tvShows)
        {
            itemContainerTvShowBinding.setTvShow(tvShows);
            itemContainerTvShowBinding.executePendingBindings();
        }
    }
}
