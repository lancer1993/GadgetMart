package com.gadget_mart.callbacks.softlogic;

import com.gadget_mart.model.softlogic.Item;

import java.util.List;

public interface ItemCallBack {
    void onSuccess(List<Item> list);
    void onFailure(String message);
}
