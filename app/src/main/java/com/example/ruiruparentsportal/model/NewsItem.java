package com.example.ruiruparentsportal.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsItem implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("s_desc")
    @Expose
    private String sDesc;
    @SerializedName("l_desc")
    @Expose
    private String lDesc;
    @SerializedName("a_date")
    @Expose
    private String aDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSDesc() {
        return sDesc;
    }

    public void setSDesc(String sDesc) {
        this.sDesc = sDesc;
    }

    public String getLDesc() {
        return lDesc;
    }

    public void setLDesc(String lDesc) {
        this.lDesc = lDesc;
    }

    public String getADate() {
        return aDate;
    }

    public void setADate(String aDate) {
        this.aDate = aDate;
    }

    @NonNull
    @Override
    public String toString() {
        return this.title + " " + this.aDate;
    }
}
