package com.httpfriccotech.lastchancediet.Exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class SelectExerciseData implements Serializable {

    @SerializedName("ExerciseType")
    @Expose
    String ExerciseType;
    @SerializedName("Minutes")
    @Expose
    String Minutes;


    public String getExerciseType() {
        return ExerciseType;
    }

    public void setExerciseType(String ExerciseType) {
        this.ExerciseType = ExerciseType;
    }

    public String getMinutes() {
        return Minutes;
    }

    public void setMinutes(String Minutes) {
        this.Minutes = Minutes;
    }

    }
