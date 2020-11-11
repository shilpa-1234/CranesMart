package com.example.cranesmart.pojo.productlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPojo {

    @SerializedName("is_special_price")
    @Expose
    private Integer isSpecialPrice;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("proID")
    @Expose
    private String proID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("hsncode")
    @Expose
    private String hsncode;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;

    public Integer getIsSpecialPrice() {
        return isSpecialPrice;
    }

    public void setIsSpecialPrice(Integer isSpecialPrice) {
        this.isSpecialPrice = isSpecialPrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

}
