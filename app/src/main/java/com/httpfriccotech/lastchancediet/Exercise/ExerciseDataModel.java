package com.httpfriccotech.lastchancediet.Exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class ExerciseDataModel {

    @SerializedName("ex_type")
    @Expose
    String ex_type;
    @SerializedName("val1")
    @Expose
    String val1;
    @SerializedName("val2")
    @Expose
    String val2="";
    @SerializedName("val3")
    @Expose
    String val3;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("exerciseID")
    @Expose
    String exerciseID;

    public String getEx_type() {
        return ex_type;
    }

    public void setEx_type(String ex_type) {
        this.ex_type = ex_type;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }
}
