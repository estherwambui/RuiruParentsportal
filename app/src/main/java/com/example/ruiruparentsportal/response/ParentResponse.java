package com.example.ruiruparentsportal.response;

import com.example.ruiruparentsportal.model.Parent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentResponse {
    @SerializedName("error")
    @Expose
    boolean error;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("parent")
    @Expose
    Parent parent;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
