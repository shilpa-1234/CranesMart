package com.example.cranesmart.pojo.Userdetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("premium_wallet_balance")
    @Expose
    private String premiumWalletBalance;
    @SerializedName("cm_points")
    @Expose
    private String cmPoints;
    @SerializedName("profile_photo")
    @Expose
    private Object profilePhoto;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPremiumWalletBalance() {
        return premiumWalletBalance;
    }

    public void setPremiumWalletBalance(String premiumWalletBalance) {
        this.premiumWalletBalance = premiumWalletBalance;
    }

    public String getCmPoints() {
        return cmPoints;
    }

    public void setCmPoints(String cmPoints) {
        this.cmPoints = cmPoints;
    }

    public Object getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Object profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}