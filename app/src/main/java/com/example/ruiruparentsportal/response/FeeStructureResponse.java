package com.example.ruiruparentsportal.response;

import com.example.ruiruparentsportal.model.FeeStructure;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStructureResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("feeStructure")
    @Expose
    private FeeStructure feeStructure;

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

    public FeeStructure getFeeStructure() {
        return feeStructure;
    }

    public void setFeeStructure(FeeStructure feeStructure) {
        this.feeStructure = feeStructure;
    }
}
