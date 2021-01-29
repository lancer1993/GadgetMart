package com.gadget_mart.callbacks.singer;

import com.gadget_mart.model.singer.Category;

import java.util.List;

public interface CategoryCallBack {
    void onSuccess(List<Category> list);
    void onFailure(String message);
}
