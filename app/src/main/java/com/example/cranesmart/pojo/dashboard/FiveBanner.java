
package com.example.cranesmart.pojo.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveBanner {

    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("banner_image_1")
    @Expose
    private String bannerImage1;
    @SerializedName("banner_image_2")
    @Expose
    private String bannerImage2;
    @SerializedName("banner_image_3")
    @Expose
    private String bannerImage3;
    @SerializedName("banner_image_4")
    @Expose
    private String bannerImage4;
    @SerializedName("banner_image_5")
    @Expose
    private String bannerImage5;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBannerImage1() {
        return bannerImage1;
    }

    public void setBannerImage1(String bannerImage1) {
        this.bannerImage1 = bannerImage1;
    }

    public String getBannerImage2() {
        return bannerImage2;
    }

    public void setBannerImage2(String bannerImage2) {
        this.bannerImage2 = bannerImage2;
    }

    public String getBannerImage3() {
        return bannerImage3;
    }

    public void setBannerImage3(String bannerImage3) {
        this.bannerImage3 = bannerImage3;
    }

    public String getBannerImage4() {
        return bannerImage4;
    }

    public void setBannerImage4(String bannerImage4) {
        this.bannerImage4 = bannerImage4;
    }

    public String getBannerImage5() {
        return bannerImage5;
    }

    public void setBannerImage5(String bannerImage5) {
        this.bannerImage5 = bannerImage5;
    }

}
