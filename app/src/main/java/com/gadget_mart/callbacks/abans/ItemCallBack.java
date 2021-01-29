package com.gadget_mart.callbacks.abans;

import com.gadget_mart.model.abans.Item;

import java.util.List;

public interface ItemCallBack {
    void onSuccess(List<Item> list);
    void onFailure(String message);
}
