package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("form")
    @Expose
    private Integer form;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("adm")
    @Expose
    private Integer adm;
    @SerializedName("maths")
    @Expose
    private Integer maths;
    @SerializedName("english")
    @Expose
    private Integer english;
    @SerializedName("kiswahili")
    @Expose
    private Integer kiswahili;
    @SerializedName("chemistry")
    @Expose
    private Integer chemistry;
    @SerializedName("cre")
    @Expose
    private Integer cre;
    @SerializedName("biology")
    @Expose
    private Integer biology;
    @SerializedName("physics")
    @Expose
    private Integer physics;
    @SerializedName("geography")
    @Expose
    private Integer geography;
    @SerializedName("history")
    @Expose
    private Integer history;
    @SerializedName("agriculture")
    @Expose
    private Integer agriculture;
    @SerializedName("home_science")
    @Expose
    private Integer homeScience;
    @SerializedName("business_studies")
    @Expose
    private Integer businessStudies;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("mean")
    @Expose
    private String mean;
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

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getAdm() {
        return adm;
    }

    public void setAdm(Integer adm) {
        this.adm = adm;
    }

    public Integer getMaths() {
        return maths;
    }

    public void setMaths(Integer maths) {
        this.maths = maths;
    }

    public Integer getEnglish() {
        return english;
    }

    public void setEnglish(Integer english) {
        this.english = english;
    }

    public Integer getKiswahili() {
        return kiswahili;
    }

    public void setKiswahili(Integer kiswahili) {
        this.kiswahili = kiswahili;
    }

    public Integer getChemistry() {
        return chemistry;
    }

    public void setChemistry(Integer chemistry) {
        this.chemistry = chemistry;
    }

    public Integer getCre() {
        return cre;
    }

    public void setCre(Integer cre) {
        this.cre = cre;
    }

    public Integer getBiology() {
        return biology;
    }

    public void setBiology(Integer biology) {
        this.biology = biology;
    }

    public Integer getPhysics() {
        return physics;
    }

    public void setPhysics(Integer physics) {
        this.physics = physics;
    }

    public Integer getGeography() {
        return geography;
    }

    public void setGeography(Integer geography) {
        this.geography = geography;
    }

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }

    public Integer getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(Integer agriculture) {
        this.agriculture = agriculture;
    }

    public Integer getHomeScience() {
        return homeScience;
    }

    public void setHomeScience(Integer homeScience) {
        this.homeScience = homeScience;
    }

    public Integer getBusinessStudies() {
        return businessStudies;
    }

    public void setBusinessStudies(Integer businessStudies) {
        this.businessStudies = businessStudies;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
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
