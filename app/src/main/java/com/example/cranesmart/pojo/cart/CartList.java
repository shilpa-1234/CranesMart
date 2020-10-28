
package com.example.cranesmart.pojo.cart;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("hsncode")
    @Expose
    private String hsncode;
    @SerializedName("tax_rule_id")
    @Expose
    private String taxRuleId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("special_price_from")
    @Expose
    private String specialPriceFrom;
    @SerializedName("special_price_to")
    @Expose
    private String specialPriceTo;
    @SerializedName("weight")
    @Expose
    private Object weight;
    @SerializedName("weight_unit")
    @Expose
    private Object weightUnit;
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("cm_percentage")
    @Expose
    private String cmPercentage;
    @SerializedName("level_percentage")
    @Expose
    private String levelPercentage;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("stock_status")
    @Expose
    private String stockStatus;
    @SerializedName("image_token")
    @Expose
    private String imageToken;
    @SerializedName("attribute_set_id")
    @Expose
    private String attributeSetId;
    @SerializedName("is_variation")
    @Expose
    private String isVariation;
    @SerializedName("variation_variable")
    @Expose
    private String variationVariable;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("approve_status")
    @Expose
    private String approveStatus;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("temp_id")
    @Expose
    private String tempId;
    @SerializedName("variation_pro_id")
    @Expose
    private String variationProId;
    @SerializedName("attribute_data")
    @Expose
    private String attributeData;
    @SerializedName("product_img")
    @Expose
    private String productImg;
    @SerializedName("mrp_price")
    @Expose
    private String mrpPrice;
    @SerializedName("is_special_price")
    @Expose
    private Integer isSpecialPrice;
    @SerializedName("deduct_cm_point")
    @Expose
    private Integer deductCmPoint;
    @SerializedName("final_price")
    @Expose
    private Integer finalPrice;
    @SerializedName("credit_cm_point")
    @Expose
    private Integer creditCmPoint;
    @SerializedName("attribute")
    @Expose
    private List<Attribute> attribute = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public String getTaxRuleId() {
        return taxRuleId;
    }

    public void setTaxRuleId(String taxRuleId) {
        this.taxRuleId = taxRuleId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getSpecialPriceFrom() {
        return specialPriceFrom;
    }

    public void setSpecialPriceFrom(String specialPriceFrom) {
        this.specialPriceFrom = specialPriceFrom;
    }

    public String getSpecialPriceTo() {
        return specialPriceTo;
    }

    public void setSpecialPriceTo(String specialPriceTo) {
        this.specialPriceTo = specialPriceTo;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Object getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Object weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getCmPercentage() {
        return cmPercentage;
    }

    public void setCmPercentage(String cmPercentage) {
        this.cmPercentage = cmPercentage;
    }

    public String getLevelPercentage() {
        return levelPercentage;
    }

    public void setLevelPercentage(String levelPercentage) {
        this.levelPercentage = levelPercentage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getImageToken() {
        return imageToken;
    }

    public void setImageToken(String imageToken) {
        this.imageToken = imageToken;
    }

    public String getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(String attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public String getIsVariation() {
        return isVariation;
    }

    public void setIsVariation(String isVariation) {
        this.isVariation = isVariation;
    }

    public String getVariationVariable() {
        return variationVariable;
    }

    public void setVariationVariable(String variationVariable) {
        this.variationVariable = variationVariable;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getVariationProId() {
        return variationProId;
    }

    public void setVariationProId(String variationProId) {
        this.variationProId = variationProId;
    }

    public String getAttributeData() {
        return attributeData;
    }

    public void setAttributeData(String attributeData) {
        this.attributeData = attributeData;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    public Integer getIsSpecialPrice() {
        return isSpecialPrice;
    }

    public void setIsSpecialPrice(Integer isSpecialPrice) {
        this.isSpecialPrice = isSpecialPrice;
    }

    public Integer getDeductCmPoint() {
        return deductCmPoint;
    }

    public void setDeductCmPoint(Integer deductCmPoint) {
        this.deductCmPoint = deductCmPoint;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getCreditCmPoint() {
        return creditCmPoint;
    }

    public void setCreditCmPoint(Integer creditCmPoint) {
        this.creditCmPoint = creditCmPoint;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

}