package com.httpfriccotech.lastchancediet.Food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodDetailResponseModel implements Serializable {

    @SerializedName("success")
    @Expose
    boolean success;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("data")
    @Expose
    FoodDetailsDataModel data;

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

    public FoodDetailsDataModel getData() {
        return data;
    }

    public void setData(FoodDetailsDataModel data) {
        this.data = data;
    }
}
