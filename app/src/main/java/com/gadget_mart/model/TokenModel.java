package com.gadget_mart.model;

import java.io.Serializable;

public class TokenModel implements Serializable {

    private String refreshedToken;

    public String getRefreshedToken() {
        return refreshedToken;
    }

    public void setRefreshedToken(String refreshedToken) {
        this.refreshedToken = refreshedToken;
    }
}
