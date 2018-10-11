package com.httpfriccotech.lastchancediet.app;

import android.app.Application;

import com.httpfriccotech.lastchancediet.util.Global;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Global.setApplicationContext(this);
    }
}
