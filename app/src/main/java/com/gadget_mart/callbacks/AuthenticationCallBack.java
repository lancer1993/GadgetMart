package com.gadget_mart.callbacks;

public interface AuthenticationCallBack {

    void onSuccessAuthentication(String message, int code);
    void onFailureAuthentication(String message, int code);

}
