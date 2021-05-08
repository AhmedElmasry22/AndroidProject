package com.example.newsapplication.Models;

import java.util.List;

public class ModelOfHome {
    private List<NewsBean> news;

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public static class NewsBean {
        /**
         * category_id : 3
         * category_title : أخبار
         * category_posts : [{"post_id":6,"post_title":"في اجتماع بـ\"البريطانية\": خميس ومكرم يبحثان آليات التواصل مع المصريين بالخارج.. وإنشاء قناة تعمل بالآليات الحديثة","post_img":"https://cizaro.net/2030/uploads/blog/posts/post-15590045895cec85ad75536.jpg","description":"عقد رئيس مجلس أمناء الجامعة البريطانية محمد فريد خميس رجل الصناعة والتعليم، اجتماعاً مع السفيرة نبيلة مكرم وزيرة الدولة للهجرة وشئون المصريين بالخارج، لبحث المشكلات التى تواجه المصريين بالخارج، وايجاد حلول عملية لها."},{"post_id":17,"post_title":"إنسانية ورحمة.. تفاصيل مبادرة \"تبني ولا تشتري\" لإنقاذ \"حيوانات الشارع\"","post_img":"https://cizaro.net/2030/uploads/blog/posts/post-15590590215ced5a4d18374.jpg","description":"تتعدد أشكال العمل الخيري التي يمكننا من خلالها تقديم خدمة للمجتمع من حولنا، وتعد أزمة \"الحيوانات الضالة\" من الأزمات المثارة داخل جمعيات الرفق بالحيوان وخارجها، والتي تحتاج إلى علاج سريع، وحلول مبتكرة، خاصة بعد الانتهاكات المستمرة التي تتعرض لها هذه الحيوانات."},{"post_id":23,"post_title":"مشاريع تخرج| تسويق بـ\"إعلام الشروق\"..  تحسين الصورة الذهنية  VS خلق صورة جديدة","post_img":"https://cizaro.net/2030/uploads/blog/posts/post-15590603045ced5f50ec48e.png","description":"يسعى برنامج التنمية المستدامة من خلال أهدافه السبعة عشر، إلى النهوض بالاقتصادات الوطنية، وخلق أسواق تنافسية، تميل -رغم تنوعها- إلى تفضيل المنتجات المحلية على حساب المنتجات المستوردة."},{"post_id":25,"post_title":"رحلات \"الشباب والرياضة\".. \"نظافة ونظام وتوفير\"","post_img":"https://cizaro.net/2030/uploads/blog/posts/post-15590605835ced60670ab23.jpg","description":"تنظم وزارة الشباب والرياضة رحلات ثقافية توعوية ضمن برنامج \"اعرف بلدك\" الذي تتبناه الوزارة نفسها، حيث تقوم بدعم هذه الرحلات مادياً، وتستهدف من خلالها زرع قيم الانتماء والولاء في نفوس الشباب المصري."}]
         */

        private int category_id;
        private String category_title;
        private List<CategoryPostsBean> category_posts;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_title() {
            return category_title;
        }

        public void setCategory_title(String category_title) {
            this.category_title = category_title;
        }

        public List<CategoryPostsBean> getCategory_posts() {
            return category_posts;
        }

        public void setCategory_posts(List<CategoryPostsBean> category_posts) {
            this.category_posts = category_posts;
        }

        public static class CategoryPostsBean {
            /**
             * post_id : 6
             * post_title : في اجتماع بـ"البريطانية": خميس ومكرم يبحثان آليات التواصل مع المصريين بالخارج.. وإنشاء قناة تعمل بالآليات الحديثة
             * post_img : https://cizaro.net/2030/uploads/blog/posts/post-15590045895cec85ad75536.jpg
             * description : عقد رئيس مجلس أمناء الجامعة البريطانية محمد فريد خميس رجل الصناعة والتعليم، اجتماعاً مع السفيرة نبيلة مكرم وزيرة الدولة للهجرة وشئون المصريين بالخارج، لبحث المشكلات التى تواجه المصريين بالخارج، وايجاد حلول عملية لها.
             */

            private int post_id;
            private String post_title;
            private String post_img;
            private String description;

            public int getPost_id() {
                return post_id;
            }

            public void setPost_id(int post_id) {
                this.post_id = post_id;
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

}
