package com.httpfriccotech.lastchancediet.Exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class SelectExerciseData implements Serializable {

    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("exerciseID")
    @Expose
    String exerciseID;

    @SerializedName("strength_training_set")
    @Expose
    String strength_training_set;

    @SerializedName("strength_training_reps_set")
    @Expose
    String strength_training_reps_set;

    @SerializedName("strength_training_weight_set")
    @Expose
    String strength_training_weight_set;

    @SerializedName("howlong")
    @Expose
    String howlong;

    @SerializedName("Calories")
    @Expose
    String Calories;

    public String getStrength_training_reps_set() {
        return strength_training_reps_set;
    }

    public void setStrength_training_reps_set(String strength_training_reps_set) {
        this.strength_training_reps_set = strength_training_reps_set;
    }

    public String getStrength_training_weight_set() {
        return strength_training_weight_set;
    }

    public void setStrength_training_weight_set(String strength_training_weight_set) {
        this.strength_training_weight_set = strength_training_weight_set;
    }

    public String getHowlong() {
        return howlong;
    }

    public void setHowlong(String howlong) {
        this.howlong = howlong;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
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

    public String getStrength_training_set() {
        return strength_training_set;
    }

    public void setStrength_training_set(String strength_training_set) {
        this.strength_training_set = strength_training_set;
    }

    }
