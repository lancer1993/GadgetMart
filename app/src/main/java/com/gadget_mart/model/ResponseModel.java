package com.gadget_mart.model;

import java.io.Serializable;

public class ResponseModel implements Serializable {

    private String response;
    private int code;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
