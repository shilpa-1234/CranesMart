
package com.example.cranesmart.pojo.productdetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttributeList {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("form_type")
    @Expose
    private String formType;
    @SerializedName("is_input_box")
    @Expose
    private String isInputBox;
    @SerializedName("Color")
    @Expose
    private List<productcartcolor> color = null;
    @SerializedName("Size")
    @Expose
    private List<ProductcartSize> size = null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getIsInputBox() {
        return isInputBox;
    }

    public void setIsInputBox(String isInputBox) {
        this.isInputBox = isInputBox;
    }

    public List<productcartcolor> getColor() {
        return color;
    }

    public void setColor(List<productcartcolor> color) {
        this.color = color;
    }

    public List<ProductcartSize> getSize() {
        return size;
    }

    public void setSize(List<ProductcartSize> size) {
        this.size = size;
    }

}