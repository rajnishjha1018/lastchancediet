package com.httpfriccotech.lastchancediet.Blog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class BlogData implements Serializable {

        private String title;
        private String blogThumbUrl;
        private String content;
        private int blogId;

        /*public BlogData(String Title, String BlogThumbUrl, String Content, int BlogId){
            title = Title;
            blogThumbUrl = BlogThumbUrl;
            content = Content;
            blogId = BlogId;
        }*/

        public String getTitle(){
            return title;
        }
        public String getBlogThumbUrl(){
            return blogThumbUrl;
        }
        public String getContent(){
            return content;
        }
        public int getBlogId(){
            return blogId;
        }

        public void setTitle(String title){
            this.title = title;
        }
        public void setBlogThumbUrl(String blogThumbUrl){this.blogThumbUrl = blogThumbUrl;}
        public void setContent(String content){
            this.content = content;
        }
        public void setBlogId(int blogId){
            this.blogId = blogId;
        }




















    /*@SerializedName("title")
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
    }*/

}
