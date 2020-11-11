package com.example.cranesmart.pojo.recharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rechargepojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
