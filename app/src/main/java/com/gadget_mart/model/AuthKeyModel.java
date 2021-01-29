package com.gadget_mart.model;

import java.io.Serializable;

public class AuthKeyModel implements Serializable {

    private String authKey;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
