package com.httpfriccotech.lastchancediet.Exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class SelectExcerciseResponseModel {

    @SerializedName("cardiovascular")
    @Expose
    List<SelectExerciseData> cardio;
    @SerializedName("strength_training")
    @Expose
    List<SelectExerciseData> strength;

    public List<SelectExerciseData> getCardio() {
        return cardio;
    }

    public void setCardio(List<SelectExerciseData> cardio) {
        this.cardio = cardio;
    }

    public List<SelectExerciseData> getStrength() {
        return strength;
    }

    public void setStrength(List<SelectExerciseData> strength) {
        this.strength = strength;
    }
}
