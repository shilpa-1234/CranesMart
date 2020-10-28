
package com.example.cranesmart.pojo.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute {

    @SerializedName("attribute_id")
    @Expose
    private String attributeId;
    @SerializedName("attribute_label")
    @Expose
    private String attributeLabel;
    @SerializedName("attribute_value_id")
    @Expose
    private String attributeValueId;
    @SerializedName("attribute_value_label")
    @Expose
    private String attributeValueLabel;
    @SerializedName("attribute_description")
    @Expose
    private String attributeDescription;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeLabel() {
        return attributeLabel;
    }

    public void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }

    public String getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(String attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public String getAttributeValueLabel() {
        return attributeValueLabel;
    }

    public void setAttributeValueLabel(String attributeValueLabel) {
        this.attributeValueLabel = attributeValueLabel;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

}