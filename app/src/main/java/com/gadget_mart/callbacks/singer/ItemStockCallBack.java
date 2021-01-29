package com.gadget_mart.callbacks.singer;

import com.gadget_mart.model.singer.ItemStock;

import java.util.List;

public interface ItemStockCallBack {
    void onSuccess(List<ItemStock> list);
    void onFailure(String message);
}
