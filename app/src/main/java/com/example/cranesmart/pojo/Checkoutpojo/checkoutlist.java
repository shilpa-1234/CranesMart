package com.example.cranesmart.pojo.Checkoutpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class checkoutlist {
    @SerializedName("txnid")
    @Expose
    private String txnid;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("encoded_order_id")
    @Expose
    private String encodedOrderId;

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getEncodedOrderId() {
        return encodedOrderId;
    }

    public void setEncodedOrderId(String encodedOrderId) {
        this.encodedOrderId = encodedOrderId;
    }

}
