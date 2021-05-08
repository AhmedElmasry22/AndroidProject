package com.example.newsapplication.Models;

import java.util.List;

public class ModelofPhotos {

    private List<AllPhotosBean> all_photos;

    public List<AllPhotosBean> getAll_photos() {
        return all_photos;
    }

    public void setAll_photos(List<AllPhotosBean> all_photos) {
        this.all_photos = all_photos;
    }

    public static class AllPhotosBean {
        /**
         * id : 1
         * photo : https://cizaro.net/2030/public/site/images/youknow/1.jpg
         */

        private int id;
        private String photo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
