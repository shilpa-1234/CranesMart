
package com.example.cranesmart.pojo.productdetail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class productcartcolor {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("attribute_input_value")
    @Expose
    private String attributeInputValue;
    @SerializedName("attribute_value")
    @Expose
    private String attributeValue;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributeInputValue() {
        return attributeInputValue;
    }

    public void setAttributeInputValue(String attributeInputValue) {
        this.attributeInputValue = attributeInputValue;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

}