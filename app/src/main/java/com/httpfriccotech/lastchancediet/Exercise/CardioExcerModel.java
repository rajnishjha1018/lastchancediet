package com.httpfriccotech.lastchancediet.Exercise;

/**
 * Created by John on 8/29/2016.
 */
public class CardioExcerModel {
    public String ExerciseType;
    public String Minutes;
    public String CaloriesBurned;

    public String getExerciseType() {
        return ExerciseType;
    }

    public void setExerciseType(String exerciseType) {
        ExerciseType = exerciseType;
    }

    public String getMinutes() {
        return Minutes;
    }

    public void setMinutes(String minutes) {
        Minutes = minutes;
    }

    public String getCaloriesBurned() {
        return CaloriesBurned;
    }

    public void setCaloriesBurned(String caloriesBurned) {
        CaloriesBurned = caloriesBurned;
    }
}