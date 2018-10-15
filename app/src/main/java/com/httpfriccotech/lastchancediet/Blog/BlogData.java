package com.httpfriccotech.lastchancediet.Blog;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class BlogData implements Serializable {
        @SerializedName("title")
        private String title;
        @SerializedName("blogThumbUrl")
        private String blogThumbUrl;
        @SerializedName("content")
        private String content;
        @SerializedName("blogId")
        private int postId;


        public String getTitle(){
            return title;
        }
        public String getBlogThumbUrl(){
            return blogThumbUrl;
        }
        public String getContent(){
            return content;
        }
        public int getPostId(){
            return postId;
        }

        public void setTitle(String title){
            this.title = title;
        }
        public void setBlogThumbUrl(String blogThumbUrl){this.blogThumbUrl = blogThumbUrl;}
        public void setContent(String content){
            this.content = content;
        }
        public void setPostId(int postId){
            this.postId = postId;
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
