package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeesStatus {
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("term")
    @Expose
    private String term;

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
