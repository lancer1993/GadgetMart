package com.gadget_mart.callbacks;

import com.gadget_mart.model.OrderDetails;

import java.util.List;

public interface OrderDetailCallBack {

    void onSuccess(List<OrderDetails> orderDetailsList);

    void onFailure(String message);
}
