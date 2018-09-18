package com.httpfriccotech.lastchancediet.Food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodDetailsDataModel implements Serializable {

    @SerializedName("DailyLimit")
    @Expose
    List<DailyLimitData> dailyLimit;
    @SerializedName("Breakfast")
    @Expose
    List<FoodData> breakfast;
    @SerializedName("Lunch")
    @Expose
    List<FoodData> lunch;
    @SerializedName("Dinner")
    @Expose
    List<FoodData> dinner;
    @SerializedName("Snacks")
    @Expose
    List<FoodData> snacks;

    public List<DailyLimitData> getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(List<DailyLimitData> dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public List<FoodData> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<FoodData> breakfast) {
        this.breakfast = breakfast;
    }

    public List<FoodData> getLunch() {
        return lunch;
    }

    public void setLunch(List<FoodData> lunch) {
        this.lunch = lunch;
    }

    public List<FoodData> getDinner() {
        return dinner;
    }

    public void setDinner(List<FoodData> dinner) {
        this.dinner = dinner;
    }

    public List<FoodData> getSnacks() {
        return snacks;
    }

    public void setSnacks(List<FoodData> snacks) {
        this.snacks = snacks;
    }
}
