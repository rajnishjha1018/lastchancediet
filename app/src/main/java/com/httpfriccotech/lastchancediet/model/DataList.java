package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataList {
    @SerializedName("CountList")
    private CountListModel CountList;
    @SerializedName("UserList")
    private ArrayList<UserListModel> UserList;
    @SerializedName("PartnerList")
    private ArrayList<UserListModel> PartnerList;

    public CountListModel getCountList() {
        return CountList;
    }

    public void setCountList(CountListModel countList) {
        CountList = countList;
    }

    public ArrayList<UserListModel> getUserList() {
        return UserList;
    }

    public void setUserList(ArrayList<UserListModel> userList) {
        UserList = userList;
    }

    public ArrayList<UserListModel> getPartnerList() {
        return PartnerList;
    }

    public void setPartnerList(ArrayList<UserListModel> partnerList) {
        PartnerList = partnerList;
    }
}
