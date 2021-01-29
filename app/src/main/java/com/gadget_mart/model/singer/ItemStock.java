package com.gadget_mart.model.singer;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemStock implements Serializable {

    public Integer getIditemStock() {
        return iditemStock;
    }

    public void setIditemStock(Integer iditemStock) {
        this.iditemStock = iditemStock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getItemStockName() {
        return itemStockName;
    }

    public void setItemStockName(String itemStockName) {
        this.itemStockName = itemStockName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getImageItem() {
        return imageItem;
    }

    public void setImageItem(String imageItem) {
        this.imageItem = imageItem;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getDeliveryTimePeriod() {
        return deliveryTimePeriod;
    }

    public void setDeliveryTimePeriod(String deliveryTimePeriod) {
        this.deliveryTimePeriod = deliveryTimePeriod;
    }

    public boolean isWarranty() {
        return warranty;
    }

    public void setWarranty(boolean warranty) {
        this.warranty = warranty;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
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

    private Integer iditemStock;
    private Category category;
    private String productCode;
    private String itemStockName;
    private String itemDescription;
    private BigDecimal itemPrice;
    private BigDecimal sellingPrice;
    private BigDecimal discount;
    private boolean availability;
    private String imageItem;
    private boolean delivery;
    private String deliveryTimePeriod;
    private boolean warranty;
    private String warrantyPeriod;
    private Long dateCreated;
    private Long lastUpdated;

}
