package com.httpfriccotech.lastchancediet.util;

import android.content.Context;

/**
 * 
 * @author Manish Kumar Singh
 * 
 */
public class Global {
	private static Context applicationContext;

	public static void setApplicationContext(Context context) {
		applicationContext = context;

	}

	public static Context getMyApplicationContext() {
        return applicationContext;

    }
    public  static String TAG="";
}
