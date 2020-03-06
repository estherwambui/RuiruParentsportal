package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
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
    @SerializedName("physics")
    @Expose
    private Integer physics;
    @SerializedName("biology")
    @Expose
    private Integer biology;
    @SerializedName("history")
    @Expose
    private Integer history;
    @SerializedName("geography")
    @Expose
    private Integer geography;
    @SerializedName("agriculture")
    @Expose
    private Integer agriculture;
    @SerializedName("business")
    @Expose
    private Integer business;
    @SerializedName("home_science")
    @Expose
    private Integer homeScience;
    @SerializedName("cre")
    @Expose
    private Integer cre;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("mean")
    @Expose
    private Integer mean;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("form")
    @Expose
    private String form;
    @SerializedName("position")
    @Expose
    private Integer position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public Integer getPhysics() {
        return physics;
    }

    public void setPhysics(Integer physics) {
        this.physics = physics;
    }

    public Integer getBiology() {
        return biology;
    }

    public void setBiology(Integer biology) {
        this.biology = biology;
    }

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }

    public Integer getGeography() {
        return geography;
    }

    public void setGeography(Integer geography) {
        this.geography = geography;
    }

    public Integer getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(Integer agriculture) {
        this.agriculture = agriculture;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getHomeScience() {
        return homeScience;
    }

    public void setHomeScience(Integer homeScience) {
        this.homeScience = homeScience;
    }

    public Integer getCre() {
        return cre;
    }

    public void setCre(Integer cre) {
        this.cre = cre;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMean() {
        return mean;
    }

    public void setMean(Integer mean) {
        this.mean = mean;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
