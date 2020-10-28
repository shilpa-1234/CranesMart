
package com.example.cranesmart.pojo.productdetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detailpojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("img")
    @Expose
    private List<String> img = null;
    @SerializedName("proID")
    @Expose
    private String proID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
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
    @SerializedName("is_special_price")
    @Expose
    private Integer isSpecialPrice;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("attribute_list")
    @Expose
    private List<AttributeList> attributeList = null;

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

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getIsSpecialPrice() {
        return isSpecialPrice;
    }

    public void setIsSpecialPrice(Integer isSpecialPrice) {
        this.isSpecialPrice = isSpecialPrice;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public List<AttributeList> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<AttributeList> attributeList) {
        this.attributeList = attributeList;
    }

}