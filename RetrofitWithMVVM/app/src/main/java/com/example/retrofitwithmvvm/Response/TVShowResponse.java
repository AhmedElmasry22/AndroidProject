package com.example.retrofitwithmvvm.Response;

import com.example.retrofitwithmvvm.Models.TVShows;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class TVShowResponse {


    @SerializedName("page")
    private int page;
    @SerializedName("pages")
    private int totalPages;
    @SerializedName("tv_shows")
    private List<TVShows> tvShows;


    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Collection<? extends TVShows> getTvShows() {
        return tvShows;
    }
}


