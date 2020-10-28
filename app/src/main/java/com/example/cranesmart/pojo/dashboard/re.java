
package com.example.cranesmart.pojo.dashboard;

import java.util.List;

import com.example.cranesmart.pojo.dashboard.CategoryDatum;
import com.example.cranesmart.pojo.dashboard.CategoryList;
import com.example.cranesmart.pojo.dashboard.FiveBanner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class re {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("category_data")
    @Expose
    private List<CategoryDatum> categoryData = null;
    @SerializedName("single_banner")
    @Expose
    private List<Object> singleBanner = null;
    @SerializedName("five_banner")
    @Expose
    private List<FiveBanner> fiveBanner = null;
    @SerializedName("categoryList")
    @Expose
    private List<CategoryList> categoryList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }

    public List<Object> getSingleBanner() {
        return singleBanner;
    }

    public void setSingleBanner(List<Object> singleBanner) {
        this.singleBanner = singleBanner;
    }

    public List<FiveBanner> getFiveBanner() {
        return fiveBanner;
    }

    public void setFiveBanner(List<FiveBanner> fiveBanner) {
        this.fiveBanner = fiveBanner;
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

}
