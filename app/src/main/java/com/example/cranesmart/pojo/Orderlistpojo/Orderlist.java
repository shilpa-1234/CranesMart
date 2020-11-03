package com.example.cranesmart.pojo.Orderlistpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orderlist {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("order_data")
        @Expose
        private List<OrderDatum> orderData = null;

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

        public List<OrderDatum> getOrderData() {
            return orderData;
        }

        public void setOrderData(List<OrderDatum> orderData) {
            this.orderData = orderData;
        }

    }
