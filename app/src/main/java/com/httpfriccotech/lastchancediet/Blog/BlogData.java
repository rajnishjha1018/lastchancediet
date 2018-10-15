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
}
