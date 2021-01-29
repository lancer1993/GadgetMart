package com.gadget_mart.callbacks.softlogic;

import com.gadget_mart.model.softlogic.Category;

import java.util.List;

public interface CategoryCallBack {
    void onSuccess(List<Category> list);
    void onFailure(String message);
}
