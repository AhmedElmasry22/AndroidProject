package com.example.moviesapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class ModelMovieDetail {


    @SerializedName("tvShow")
    TvShowsBean modelMovieDetail;

    public TvShowsBean getModelMovieDetail() {
        return modelMovieDetail;
    }
  public static class TvShowsBean {
      @SerializedName("url")
      private String url;
      @SerializedName("description")
      private String description;
      @SerializedName("start_date")
      private String start_data;
      @SerializedName("rating")
      private String rating;
      @SerializedName("pictures")
      String[] pictures;

      @SerializedName("episodes")
      List<Episodes> episodes;

      public String[] getPictures() {
          return pictures;
      }

      public String getStart_data() {
          return start_data;
      }

      public String getRating() {
          return rating;
      }



      public List<Episodes> getEpisodes() {
          return episodes;
      }

      public String getUrl() {
          return url;
      }

      public String getDescription() {
          return description;
      }

      public static class Episodes {
          @SerializedName("season")
          private int Season;
          @SerializedName("episode")
          private int episodo;
          @SerializedName("name")
          private String name;
          @SerializedName("air_date")
          private String air_data;


      }


  }

}

