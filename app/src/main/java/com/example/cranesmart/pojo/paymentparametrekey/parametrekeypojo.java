package com.example.cranesmart.pojo.paymentparametrekey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parametrekeypojo {
    @SerializedName("payu_merchant_id")
    @Expose
    private String payuMerchantId;
    @SerializedName("payu_merchant_key")
    @Expose
    private String payuMerchantKey;
    @SerializedName("payu_merchant_salt")
    @Expose
    private String payuMerchantSalt;

    public String getPayuMerchantId() {
        return payuMerchantId;
    }

    public void setPayuMerchantId(String payuMerchantId) {
        this.payuMerchantId = payuMerchantId;
    }

    public String getPayuMerchantKey() {
        return payuMerchantKey;
    }

    public void setPayuMerchantKey(String payuMerchantKey) {
        this.payuMerchantKey = payuMerchantKey;
    }

    public String getPayuMerchantSalt() {
        return payuMerchantSalt;
    }

    public void setPayuMerchantSalt(String payuMerchantSalt) {
        this.payuMerchantSalt = payuMerchantSalt;
    }

}