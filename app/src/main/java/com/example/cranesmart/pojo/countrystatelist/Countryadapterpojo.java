
package com.example.cranesmart.pojo.countrystatelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countryadapterpojo{

    @SerializedName("countryID")
    @Expose
    private String countryID;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("name")
    @Expose
    private String name;

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}