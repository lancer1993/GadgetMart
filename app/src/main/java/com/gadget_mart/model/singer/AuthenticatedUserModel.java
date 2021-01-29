package com.gadget_mart.model.singer;

import java.io.Serializable;

public class AuthenticatedUserModel implements Serializable {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String token;
    private User user;
}
