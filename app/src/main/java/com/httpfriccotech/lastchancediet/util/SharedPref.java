package com.httpfriccotech.lastchancediet.util;

/*
 * Class is used to save and get the data to the preference store
 *
 * @author Manish Kumar Singh
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String PREF_USER_INFO = "LAST_CHANCE_DIET";


    public interface PREF_KEYS {
        String email = "email";
        String nickName = "nickName";
        String displayName = "displayName";
        String token = "token";
        String userId = "userId";
        String password = "password";
        String userType = "setUserType";
        String payStatus = "payStatus";
        String foodType ="foodType";

    }
    public static String getfoodType(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.foodType, "");
        return referral;
    }
    public static void setfoodType(Context context, String str){
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.foodType, str);
        editor.commit();
    }
    public static String getPayStatus(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.payStatus, "");
        return referral;
    }

    public static void setPayStatus(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.payStatus, str);
        editor.commit();
    }

    public static String getUserType(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.userType, "");
        return referral;
    }

    public static void setUserType(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.userType, str);
        editor.commit();
    }

    public static String getPassword(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.password, "");
        return referral;
    }

    public static void setPassword(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.password, str);
        editor.commit();
    }

    public static String getUserId(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.userId, "122");
        return referral;
    }

    public static void setUserId(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.userId, str);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.email, "");
        return referral;
    }

    public static void setUserEmail(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.email, str);
        editor.commit();
    }

    public static String getUserName(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.nickName, "");
        return referral;
    }

    public static void setUserName(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.nickName, str);
        editor.commit();
    }

    public static String getDisplayName(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.displayName, "");
        return referral;
    }

    public static void setDisplayName(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.displayName, str);
        editor.apply();
    }

    public static String getToken(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        String referral = preferences.getString(PREF_KEYS.token, "");
        return referral;
    }

    public static void setToken(Context context, String str) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_KEYS.token, str);
        editor.apply();
    }
}