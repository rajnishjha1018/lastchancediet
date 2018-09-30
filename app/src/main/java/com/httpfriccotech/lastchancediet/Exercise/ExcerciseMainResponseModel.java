package com.httpfriccotech.lastchancediet.Exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExcerciseMainResponseModel {
    @SerializedName("data")
    @Expose
    SelectExcerciseResponseModel excersise;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("success")
    @Expose
    Boolean success;



    public String getMessage() {
        return message;
    }

    public SelectExcerciseResponseModel getExcersise() {
        return excersise;
    }

    public void setExcersise(SelectExcerciseResponseModel excersise) {
        this.excersise = excersise;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
