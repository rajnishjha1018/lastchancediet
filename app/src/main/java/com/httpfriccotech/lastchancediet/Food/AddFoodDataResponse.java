package com.httpfriccotech.lastchancediet.Food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class AddFoodDataResponse implements Serializable {

    @SerializedName("success")
    @Expose
    String success;
    @SerializedName("error")
    @Expose
    String error;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String url) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
