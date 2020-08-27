package com.example.ruiruparentsportal.response;

import com.example.ruiruparentsportal.model.FeeStructure;
import com.example.ruiruparentsportal.model.FeesStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStatusResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("feeStatus")
    @Expose
    private FeesStatus feeStatus;

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

    public FeesStatus getFeeStructure() {
        return feeStatus;
    }

    public void setFeeStructure(FeesStatus feeStructure) {
        this.feeStatus = feeStructure;
    }
}
