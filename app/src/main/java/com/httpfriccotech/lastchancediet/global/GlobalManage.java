package com.httpfriccotech.lastchancediet.global;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class GlobalManage {
    private String userId;
    private String userName;
    private String password;
    private GlobalManage() {
    }

    private static GlobalManage manager;

    public static GlobalManage getInstance() {
        if (manager == null) {
            manager = new GlobalManage();
        }
        return manager;
    }

    public String getUserId() {return userId;}
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
