package com.example.ruiruparentsportal.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("adm_no")
    @Expose
    private Integer admNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("form")
    @Expose
    private String form;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdmNo() {
        return admNo;
    }

    public void setAdmNo(Integer admNo) {
        this.admNo = admNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.admNo);
    }
}
