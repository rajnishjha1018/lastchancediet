package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

public class GenericRequestModel {
    @SerializedName("userId")
    String userId;
    @SerializedName("CurrentDate")
    String CurrentDate;
    @SerializedName("refno")
    String refno;

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }
}
