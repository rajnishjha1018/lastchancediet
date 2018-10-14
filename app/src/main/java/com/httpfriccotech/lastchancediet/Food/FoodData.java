package com.httpfriccotech.lastchancediet.Food;

/**
 * Created by John on 8/29/2016.
 */
public class FoodData {
    public String FoodType;
    public String Title;
    public String Protein;
    public String Carb;
    public String Fat;
    public String Fiber;
    public String PostId;
    public String isHeader;

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getCarb() {
        return Carb;
    }

    public void setCarb(String carb) {
        Carb = carb;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getFiber() {
        return Fiber;
    }

    public void setFiber(String fiber) {
        Fiber = fiber;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(String isHeader) {
        this.isHeader = isHeader;
    }
}