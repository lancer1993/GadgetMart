package com.gadget_mart.callbacks;

public interface ResponseCallBack {

    void onSuccess(String message, int code);
    void onFailure(String message, int code);
}
