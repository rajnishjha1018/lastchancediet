package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("token")
    String token;
    @SerializedName("user_email")
    String user_email;
    @SerializedName("user_nicename")
    String user_nicename;
    @SerializedName("user_display_name")
    String user_display_name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
    }

    public String getUser_display_name() {
        return user_display_name;
    }

    public void setUser_display_name(String user_display_name) {
        this.user_display_name = user_display_name;
    }
}
