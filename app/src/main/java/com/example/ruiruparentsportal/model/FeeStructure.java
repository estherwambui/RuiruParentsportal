package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStructure {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("form")
    @Expose
    private Integer form;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("tuition")
    @Expose
    private Integer tuition;
    @SerializedName("registration")
    @Expose
    private Integer registration;
    @SerializedName("library")
    @Expose
    private Integer library;
    @SerializedName("activity")
    @Expose
    private Integer activity;
    @SerializedName("exams")
    @Expose
    private Integer exams;
    @SerializedName("development")
    @Expose
    private Integer development;
    @SerializedName("caution")
    @Expose
    private Integer caution;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("clubs_and_societies")
    @Expose
    private Integer clubsAndSocieties;
    @SerializedName("boarding")
    @Expose
    private Integer boarding;
    @SerializedName("total")
    @Expose
    private Integer total;
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

    public Integer getTuition() {
        return tuition;
    }

    public void setTuition(Integer tuition) {
        this.tuition = tuition;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    public Integer getLibrary() {
        return library;
    }

    public void setLibrary(Integer library) {
        this.library = library;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getExams() {
        return exams;
    }

    public void setExams(Integer exams) {
        this.exams = exams;
    }

    public Integer getDevelopment() {
        return development;
    }

    public void setDevelopment(Integer development) {
        this.development = development;
    }

    public Integer getCaution() {
        return caution;
    }

    public void setCaution(Integer caution) {
        this.caution = caution;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getClubsAndSocieties() {
        return clubsAndSocieties;
    }

    public void setClubsAndSocieties(Integer clubsAndSocieties) {
        this.clubsAndSocieties = clubsAndSocieties;
    }

    public Integer getBoarding() {
        return boarding;
    }

    public void setBoarding(Integer boarding) {
        this.boarding = boarding;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
