package com.httpfriccotech.lastchancediet.Recepies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.httpfriccotech.lastchancediet.Exercise.ExerciseDataModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RAJNISH on 08/16/2018.
 */

public class RecepieModel implements Serializable {

    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("blogThumbUrl")
    @Expose
    String blogThumbUrl;
    @SerializedName("content")
    @Expose
    String content;
    @SerializedName("blogId")
    @Expose
    String blogId;

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

    public String getBlogThumbUrl() {
        return blogThumbUrl;
    }

    public void setBlogThumbUrl(String blogThumbUrl) {
        this.blogThumbUrl = blogThumbUrl;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setblogId(String blogId) {
        this.blogId = blogId;
    }

}
