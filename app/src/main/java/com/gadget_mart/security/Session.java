package com.gadget_mart.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String username){
        prefs.edit().putString("username",username);
        prefs.edit().apply();
    }

    public String getUsername(){
        String username = prefs.getString("username","");
        return username;
    }

}
