package com.example.cranesmart.pojo.paymentparametrekey;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parametrekey {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private parametrekeypojo data;

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

    public parametrekeypojo getData() {
        return data;
    }

    public void setData(parametrekeypojo data) {
        this.data = data;
    }

}
