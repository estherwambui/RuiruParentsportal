package com.example.ruiruparentsportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStatusResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("fees_status")
    @Expose
    private FeesStatus feesStatus;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FeesStatus getFeesStatus() {
        return feesStatus;
    }

    public void setFeesStatus(FeesStatus feesStatus) {
        this.feesStatus = feesStatus;
    }
}
