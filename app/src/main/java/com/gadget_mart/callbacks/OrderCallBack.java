package com.gadget_mart.callbacks;

import com.gadget_mart.model.Orders;

import java.util.List;

public interface OrderCallBack {

    void onSuccess(List<Orders> list);

    void onFailure(String message);
}
