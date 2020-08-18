
package com.example.cranesmart.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_slug")
    @Expose
    private String productSlug;
    @SerializedName("product_img")
    @Expose
    private String productImg;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special_price_status")
    @Expose
    private Integer specialPriceStatus;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSlug() {
        return productSlug;
    }

    public void setProductSlug(String productSlug) {
        this.productSlug = productSlug;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getSpecialPriceStatus() {
        return specialPriceStatus;
    }

    public void setSpecialPriceStatus(Integer specialPriceStatus) {
        this.specialPriceStatus = specialPriceStatus;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

}
