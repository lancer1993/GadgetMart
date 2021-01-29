package com.gadget_mart.security;

import android.content.Context;

public class TokenIdentifier implements java.io.Serializable {

    private static String TOKEN;
    private static String REFRESH_TOKEN;

    public TokenIdentifier() {
    }

    public static void setTOKEN(String TOKEN, Context context) {
        SaveSharedPreference.setSaveSharedPreference(context, com.gadget_mart.security.CommonInterface.PREF_TOKEN, TOKEN);
        com.gadget_mart.security.TokenIdentifier.TOKEN = TOKEN;
    }

    public static String getTOKEN(Context context) {
        if (TOKEN == null) {
            TOKEN = SaveSharedPreference.getSaveSharedPreference(context, com.gadget_mart.security.CommonInterface.PREF_TOKEN);
        }
        return TOKEN;
    }

    public static void removeTOKEN(Context context) {
        SaveSharedPreference.removeSharedPreference(context, com.gadget_mart.security.CommonInterface.PREF_TOKEN);
    }

    public static void setRefreshToken(String REFRESH_TOKEN, Context context) {
        SaveSharedPreference.setSaveSharedPreference(context, com.gadget_mart.security.CommonInterface.PREF_TOKEN, TOKEN);
        com.gadget_mart.security.TokenIdentifier.REFRESH_TOKEN = REFRESH_TOKEN;
    }

    public static String getRefreshToken(Context context) {
        if (REFRESH_TOKEN == null) {
            REFRESH_TOKEN = SaveSharedPreference.getSaveSharedPreference(context, com.gadget_mart.security.CommonInterface.PREF_REFRESH_TOKEN);
        }
        return REFRESH_TOKEN;
    }

    public static void removeRefreshToken(Context context) {
        SaveSharedPreference.removeSharedPreference(context, com.gadget_mart.security.CommonInterface.PREF_REFRESH_TOKEN);
    }

    public static String getREFRESHTOKEN() {
        return REFRESH_TOKEN;
    }

    public static void setREFRESHTOKEN(String REFRESHTOKEN) {
        com.gadget_mart.security.TokenIdentifier.REFRESH_TOKEN = REFRESHTOKEN;
    }

}
