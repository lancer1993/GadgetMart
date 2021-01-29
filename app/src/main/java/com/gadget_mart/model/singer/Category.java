package com.gadget_mart.model.singer;

import java.io.Serializable;

public class Category implements Serializable {

    public Integer getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(Integer idcategory) {
        this.idcategory = idcategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private Integer idcategory;
    private String categoryName;

}
