package com.gadget_mart.security.softlogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SoftlogicsSaveSharedPreference {

    static final String PREF_USER_NAME= "softlogic_username";
    static final String PREF_USER_PASSWORD="softlogic_password";
    static final String PREF_USER_ID = "softlogic_userId";
    static final String PREF_USER_FULL_NAME = "softlogic_userFullName";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setPassword(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_PASSWORD, password);
        editor.commit();
    }

    public static String getPassword(Context context){
        return getSharedPreferences(context).getString(PREF_USER_PASSWORD,"");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void removeUsername(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREF_USER_NAME);
        editor.commit();
    }

    public static void removePassword(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREF_USER_PASSWORD);
        editor.commit();
    }

    public static void setSaveSharedPreference(Context context, String type, String args){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(type,args);
        editor.commit();
    }

    public static String getSaveSharedPreference(Context context, String args){
        return getSharedPreferences(context).getString(args,"");
    }

    public static void removeSharedPreference(Context context, String type){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(type);
        editor.commit();
    }

    public static void setUserId(Context context, int userId){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(PREF_USER_ID, userId);
        editor.commit();
    }

    public static int getUserId(Context context){
        return getSharedPreferences(context).getInt(PREF_USER_ID,0);
    }

    public static void removeUserId(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREF_USER_ID);
        editor.commit();
    }

    public static void setUserFullName(Context context, String name){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_FULL_NAME, name);
        editor.commit();
    }

    public static String getUserFullName(Context context){
        return getSharedPreferences(context).getString(PREF_USER_FULL_NAME,"");
    }

    public static void removeUserFullName(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREF_USER_FULL_NAME);
        editor.commit();
    }

}
