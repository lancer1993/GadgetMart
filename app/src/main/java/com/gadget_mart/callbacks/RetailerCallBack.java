package com.gadget_mart.callbacks;

import com.gadget_mart.model.RetailerModel;

import java.util.List;

public interface RetailerCallBack {

    void onSuccess(List<RetailerModel> list);
    void onFailure(String message);
}
