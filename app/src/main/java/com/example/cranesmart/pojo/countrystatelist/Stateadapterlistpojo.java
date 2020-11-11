package com.example.cranesmart.pojo.countrystatelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stateadapterlistpojo {

    @SerializedName("stateID")
    @Expose
    private String stateID;
    @SerializedName("name")
    @Expose
    private String name;

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}