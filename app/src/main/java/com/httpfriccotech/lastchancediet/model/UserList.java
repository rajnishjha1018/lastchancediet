package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

public class UserList {
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("date")
    String date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
