package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
