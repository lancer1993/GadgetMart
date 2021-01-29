package com.gadget_mart.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MainOrderDetails implements Serializable {

    private String productCode;
    private String productImage;
    private int quantity;
    private String productWarrentyPeriod;
    private String deliveryPeriod;
    private BigDecimal itemTotal;
    private String productName;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductWarrentyPeriod() {
        return productWarrentyPeriod;
    }

    public void setProductWarrentyPeriod(String productWarrentyPeriod) {
        this.productWarrentyPeriod = productWarrentyPeriod;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public BigDecimal getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(BigDecimal itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
