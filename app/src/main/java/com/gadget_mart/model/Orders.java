package com.gadget_mart.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Orders implements Serializable {

    private Integer idorder;
    private RetailerModel retailers;
    private User user;
    private Long orderDate;
    private BigDecimal orderAmount;
    private Long dateCreated;
    private Long lastUpdated;

    public Integer getIdorder() {
        return idorder;
    }

    public void setIdorder(Integer idorder) {
        this.idorder = idorder;
    }

    public RetailerModel getRetailers() {
        return retailers;
    }

    public void setRetailers(RetailerModel retailers) {
        this.retailers = retailers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Long orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
