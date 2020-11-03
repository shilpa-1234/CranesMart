package com.example.cranesmart.pojo.PremiumWallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallethistoryadapter {

    @SerializedName("before_balance")
    @Expose
    private String beforeBalance;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("after_balance")
    @Expose
    private String afterBalance;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("description")
    @Expose
    private String description;

    public String getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(String beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(String afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}