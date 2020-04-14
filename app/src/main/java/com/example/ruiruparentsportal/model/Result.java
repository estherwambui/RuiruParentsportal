package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("adm_no")
    @Expose
    private Integer adm_no;
    @SerializedName("maths")
    @Expose
    private String maths;
    @SerializedName("english")
    @Expose
    private String english;
    @SerializedName("kiswahili")
    @Expose
    private String kiswahili;
    @SerializedName("chemistry")
    @Expose
    private String chemistry;
    @SerializedName("physics")
    @Expose
    private String physics;
    @SerializedName("biology")
    @Expose
    private String biology;
    @SerializedName("history")
    @Expose
    private String history;
    @SerializedName("geography")
    @Expose
    private String geography;
    @SerializedName("agriculture")
    @Expose
    private String agriculture;
    @SerializedName("business")
    @Expose
    private String business;
    @SerializedName("home_science")
    @Expose
    private String homeScience;
    @SerializedName("cre")
    @Expose
    private String cre;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("mean")
    @Expose
    private String mean;
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
    @SerializedName("students")
    @Expose
    private Integer students;
    @SerializedName("student_name")
    @Expose
    private String student_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdm_no() {
        return adm_no;
    }

    public void setAdm_no(Integer adm_no) {
        this.adm_no = adm_no;
    }

    public String getMaths() {
        return maths;
    }

    public void setMaths(String maths) {
        this.maths = maths;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKiswahili() {
        return kiswahili;
    }

    public void setKiswahili(String kiswahili) {
        this.kiswahili = kiswahili;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    public String getPhysics() {
        return physics;
    }

    public void setPhysics(String physics) {
        this.physics = physics;
    }

    public String getBiology() {
        return biology;
    }

    public void setBiology(String biology) {
        this.biology = biology;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    public String getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(String agriculture) {
        this.agriculture = agriculture;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getHomeScience() {
        return homeScience;
    }

    public void setHomeScience(String homeScience) {
        this.homeScience = homeScience;
    }

    public String getCre() {
        return cre;
    }

    public void setCre(String cre) {
        this.cre = cre;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
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

    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
}
