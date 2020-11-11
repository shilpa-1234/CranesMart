package com.example.cranesmart.pojo.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addaddress {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}