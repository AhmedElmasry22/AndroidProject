package com.example.foodordering.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodData {

    @SerializedName("popular")
    @Expose
    private List<Model_Popular> popular = null;

    @SerializedName("recommended")
    @Expose
    private List<Model_Recommended> recommended = null;

    @SerializedName("allmenu")
    @Expose
    private List<Model_All_Menu> allmenu = null;

    public List<Model_Popular> getPopular() {
        return popular;
    }

    public void setPopular(List<Model_Popular> popular) {
        this.popular = popular;
    }

    public List<Model_Recommended> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<Model_Recommended> recommended) {
        this.recommended = recommended;
    }

    public List<Model_All_Menu> getAllmenu() {
        return allmenu;
    }

    public void setAllmenu(List<Model_All_Menu> allmenu) {
        this.allmenu = allmenu;
    }
}
