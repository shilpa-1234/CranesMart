package com.example.cranesmart.pojo.operatorlist.Prepaid;


import java.util.List;

import com.example.cranesmart.pojo.operatorlist.Prepaid.operatordata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operatorlist {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<operatordata> data = null;

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

    public List<operatordata> getData() {
        return data;
    }

    public void setData(List<operatordata> data) {
        this.data = data;
    }

}