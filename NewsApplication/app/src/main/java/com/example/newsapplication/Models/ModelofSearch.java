package com.example.newsapplication.Models;

import java.util.List;

public class ModelofSearch {


    private List<PostsBean> posts;

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public static class PostsBean {
        /**
         * post_id : 1
         * category_post : روشتة
         * post_title : 8 نصائح لرعاية طفلك "حديث الولادة": هز الرضيع يسبب "ارتجاج المخ"
         * post_img : https://cizaro.net/2030/uploads/blog/posts/post-15590001365cec744886199.jpg
         * description : بعد "الولادة"، تنتهي آلام "الحمل" ومشكلاته، وتبدأ مخاوف "الرعاية" في أخذ طريقها إلى قلب الأم وعقلها، وبين حرصها الزائد، واهمالها غير المقصود، تقع الأم المصرية أثناء تعاملها مع طفلها حديث الولادة، في أخطاء شائعة.
         */

        private int post_id;
        private String category_post;
        private String post_title;
        private String post_img;
        private String description;

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public String getCategory_post() {
            return category_post;
        }

        public void setCategory_post(String category_post) {
            this.category_post = category_post;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_img() {
            return post_img;
        }

        public void setPost_img(String post_img) {
            this.post_img = post_img;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
