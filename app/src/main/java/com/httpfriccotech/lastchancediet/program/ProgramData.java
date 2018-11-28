package com.httpfriccotech.lastchancediet.program;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProgramData implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("parmlink")
    private String parmlink;
    @SerializedName("postId")
    private int postId;
    @SerializedName("isActiveProgram")
    private boolean isActiveProgram;
    @SerializedName("isAllowedProgram")
    private int isAllowedProgram;

    public int getIsAllowedProgram() {
        return isAllowedProgram;
    }

    public void setIsAllowedProgram(int isAllowedProgram) {
        this.isAllowedProgram = isAllowedProgram;
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

    public String getParmlink() {
        return parmlink;
    }

    public void setParmlink(String parmlink) {
        this.parmlink = parmlink;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isActiveProgram() {
        return isActiveProgram;
    }

    public void setActiveProgram(boolean activeProgram) {
        isActiveProgram = activeProgram;
    }
}
