package com.gadget_mart.callbacks.abans;

import com.gadget_mart.model.abans.Category;

import java.util.List;

public interface CategoryCallBack {
    void onSuccess(List<Category> list);
    void onFailure(String message);
}
