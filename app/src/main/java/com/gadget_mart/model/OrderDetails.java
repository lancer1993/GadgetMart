package com.gadget_mart.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetails implements Serializable {

    private Integer idorderDetails;
    private Orders orders;
    private String productCode;
    private String productImage;
    private int quantity;
    private BigDecimal itemTotal;
    private String productWarrentyPeriod;
    private String deliveryPeriod;
    private Long lastUpdatedDateTime;
    private Long createdDateTime;

    public Integer getIdorderDetails() {
        return idorderDetails;
    }

    public void setIdorderDetails(Integer idorderDetails) {
        this.idorderDetails = idorderDetails;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

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

    public BigDecimal getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(BigDecimal itemTotal) {
        this.itemTotal = itemTotal;
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

    public Long getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Long lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public Long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
