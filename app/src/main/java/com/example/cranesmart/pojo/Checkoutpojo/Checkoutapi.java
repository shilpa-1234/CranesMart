package com.example.cranesmart.pojo.Checkoutpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checkoutapi {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private checkoutlist data;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public checkoutlist getData() {
        return data;
    }

    public void setData(checkoutlist data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
