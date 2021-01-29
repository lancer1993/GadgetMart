package com.gadget_mart.model;

import java.io.Serializable;
import java.util.List;

public class MainOrder implements Serializable {

    private int idUser;
    private int idReatilers;
    private double totalAmount;
    private List<MainOrderDetails> list;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdReatilers() {
        return idReatilers;
    }

    public void setIdReatilers(int idReatilers) {
        this.idReatilers = idReatilers;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<MainOrderDetails> getList() {
        return list;
    }

    public void setList(List<MainOrderDetails> list) {
        this.list = list;
    }
}
