package com.gadget_mart.model;

import java.io.Serializable;

public class RetailerModel implements Serializable {

    private Integer idretailers;
    private String retailerName;
    private String retailerImage;
    private String email;
    private String phone;
    private String address;
    private boolean available;
    private Long dateCraeted;
    private Long lastUpdated;

    public Integer getIdretailers() {
        return idretailers;
    }

    public void setIdretailers(Integer idretailers) {
        this.idretailers = idretailers;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerImage() {
        return retailerImage;
    }

    public void setRetailerImage(String retailerImage) {
        this.retailerImage = retailerImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getDateCraeted() {
        return dateCraeted;
    }

    public void setDateCraeted(Long dateCraeted) {
        this.dateCraeted = dateCraeted;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
