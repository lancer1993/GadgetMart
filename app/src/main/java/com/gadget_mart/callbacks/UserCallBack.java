package com.gadget_mart.callbacks;

import com.gadget_mart.model.User;

public interface UserCallBack {

    void onSuccess(User user);
    void onFailure(String message);

}
