
package com.example.cranesmart.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryList {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("banner")
    @Expose
    private List<Banner> banner = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

}
