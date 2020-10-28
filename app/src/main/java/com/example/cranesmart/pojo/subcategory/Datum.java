package com.example.cranesmart.pojo.subcategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


        @SerializedName("catID")
        @Expose
        private String catID;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("category_icon")
        @Expose
        private String categoryIcon;

        public String getCatID () {
        return catID;
    }

        public void setCatID (String catID){
        this.catID = catID;
    }

        public String getTitle () {
        return title;
    }

        public void setTitle (String title){
        this.title = title;
    }

        public String getSlug () {
        return slug;
    }

        public void setSlug (String slug){
        this.slug = slug;
    }

        public String getCategoryIcon () {
        return categoryIcon;
    }

        public void setCategoryIcon (String categoryIcon){
        this.categoryIcon = categoryIcon;
    }


}