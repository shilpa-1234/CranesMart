package com.example.cranesmart.pojo.Orderlistpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDatum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_display_id")
    @Expose
    private String orderDisplayId;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("encoded_order_id")
    @Expose
    private String encodedOrderId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("total_item")
    @Expose
    private String totalItem;
    @SerializedName("total_mrp_price")
    @Expose
    private String totalMrpPrice;
    @SerializedName("total_item_price")
    @Expose
    private String totalItemPrice;
    @SerializedName("total_base_price")
    @Expose
    private String totalBasePrice;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("delivery_price")
    @Expose
    private String deliveryPrice;
    @SerializedName("tax_percentage")
    @Expose
    private String taxPercentage;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("cm_percentage")
    @Expose
    private String cmPercentage;
    @SerializedName("credit_cm_point")
    @Expose
    private String creditCmPoint;
    @SerializedName("deduct_cm_point")
    @Expose
    private String deductCmPoint;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("final_price")
    @Expose
    private String finalPrice;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_request_id")
    @Expose
    private String paymentRequestId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private Object updated;
    @SerializedName("add_name")
    @Expose
    private String addName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("status_title")
    @Expose
    private String statusTitle;
    @SerializedName("productInfo")
    @Expose
    private List<ProductInfo> productInfo = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDisplayId() {
        return orderDisplayId;
    }

    public void setOrderDisplayId(String orderDisplayId) {
        this.orderDisplayId = orderDisplayId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEncodedOrderId() {
        return encodedOrderId;
    }

    public void setEncodedOrderId(String encodedOrderId) {
        this.encodedOrderId = encodedOrderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalMrpPrice() {
        return totalMrpPrice;
    }

    public void setTotalMrpPrice(String totalMrpPrice) {
        this.totalMrpPrice = totalMrpPrice;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getTotalBasePrice() {
        return totalBasePrice;
    }

    public void setTotalBasePrice(String totalBasePrice) {
        this.totalBasePrice = totalBasePrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getCmPercentage() {
        return cmPercentage;
    }

    public void setCmPercentage(String cmPercentage) {
        this.cmPercentage = cmPercentage;
    }

    public String getCreditCmPoint() {
        return creditCmPoint;
    }

    public void setCreditCmPoint(String creditCmPoint) {
        this.creditCmPoint = creditCmPoint;
    }

    public String getDeductCmPoint() {
        return deductCmPoint;
    }

    public void setDeductCmPoint(String deductCmPoint) {
        this.deductCmPoint = deductCmPoint;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentRequestId() {
        return paymentRequestId;
    }

    public void setPaymentRequestId(String paymentRequestId) {
        this.paymentRequestId = paymentRequestId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Object getUpdated() {
        return updated;
    }

    public void setUpdated(Object updated) {
        this.updated = updated;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }

}