package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeesStatus {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("adm")
    @Expose
    private Integer adm;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("expected")
    @Expose
    private Integer expected;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("form")
    @Expose
    private Integer form;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdm() {
        return adm;
    }

    public void setAdm(Integer adm) {
        this.adm = adm;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getExpected() {
        return expected;
    }

    public void setExpected(Integer expected) {
        this.expected = expected;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
