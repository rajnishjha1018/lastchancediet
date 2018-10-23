package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

public class AdminDashBordModel {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataList data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataList getData() {
        return data;
    }

    public void setData(DataList data) {
        this.data = data;
    }
}
