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
}
