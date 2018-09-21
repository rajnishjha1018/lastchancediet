package com.httpfriccotech.lastchancediet.ReadMore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class BlogByIdResponseData implements Serializable {

    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("blogThumbUrl")
    @Expose
    String blogThumbUrl;
    @SerializedName("content")
    @Expose
    String content;
    @SerializedName("postId")
    @Expose
    String postId;
    ////
    @SerializedName("author")
    @Expose
    String author;
    @SerializedName("postDate")
    @Expose
    String postDate;
    @SerializedName("postViews")
    @Expose
    String postViews;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogThumbUrl() {
        return blogThumbUrl;
    }
    public void setBlogThumbUrl(String blogThumbUrl) {
        this.blogThumbUrl = blogThumbUrl;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }
    public void setPostId(String blogId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostDate() {
        return postDate;
    }
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostViews() {return postViews;}
    public void setPostViews(String postViews) {
        this.postViews = postViews;
    }

}