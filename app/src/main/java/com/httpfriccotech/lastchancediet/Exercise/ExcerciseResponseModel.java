package com.httpfriccotech.lastchancediet.Exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class ExcerciseResponseModel {

    @SerializedName("Cardiovascular")
    @Expose
    List<ExerciseDataModel> cardio;
    @SerializedName("strength_training")
    @Expose
    List<ExerciseDataModel> strength;

    public List<ExerciseDataModel> getCardio() {
        return cardio;
    }

    public void setCardio(List<ExerciseDataModel> cardio) {
        this.cardio = cardio;
    }

    public List<ExerciseDataModel> getStrength() {
        return strength;
    }

    public void setStrength(List<ExerciseDataModel> strength) {
        this.strength = strength;
    }
}
