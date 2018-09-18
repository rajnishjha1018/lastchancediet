package com.httpfriccotech.lastchancediet.Food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class SelectFoodData implements Serializable {

    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("content")
    @Expose
    String content;
    @SerializedName("protein")
    @Expose
    String protein;
    @SerializedName("carb")
    @Expose
    String carb;
    @SerializedName("fiber")
    @Expose
    String fiber;
    @SerializedName("fat")
    @Expose
    String fat;
    @SerializedName("postId")
    @Expose
    Integer postId = -1;
    @SerializedName("new")
    @Expose
    boolean isAddedFood = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarb() {
        return carb;
    }

    public void setCarb(String carb) {
        this.carb = carb;
    }

    public String getFiber() {
        return fiber;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public boolean isAddedFood() {
        return isAddedFood;
    }

    public void setAddedFood(boolean addedFood) {
        isAddedFood = addedFood;
    }
}
