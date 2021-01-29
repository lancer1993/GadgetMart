package com.gadget_mart.security.abans;

import android.content.Context;

import com.gadget_mart.security.CommonInterface;

public class AbansTokenIdentifier implements java.io.Serializable {

    private static String ABANS_TOKEN;
    private static String ABANS_REFRESH_TOKEN;

    public AbansTokenIdentifier() {
    }

    public static void setTOKEN(String TOKEN, Context context) {
        AbansSaveSharedPreference.setSaveSharedPreference(context, CommonInterface.ABANS_TOKEN, TOKEN);
        AbansTokenIdentifier.ABANS_TOKEN = TOKEN;
    }

    public static String getTOKEN(Context context) {
        if (ABANS_TOKEN == null) {
            ABANS_TOKEN = AbansSaveSharedPreference.getSaveSharedPreference(context, CommonInterface.ABANS_TOKEN);
        }
        return ABANS_TOKEN;
    }

    public static void removeTOKEN(Context context) {
        AbansSaveSharedPreference.removeSharedPreference(context, CommonInterface.ABANS_TOKEN);
    }

    public static void setRefreshToken(String REFRESH_TOKEN, Context context) {
        AbansSaveSharedPreference.setSaveSharedPreference(context, CommonInterface.ABANS_REFRESH_TOKEN, ABANS_TOKEN);
        AbansTokenIdentifier.ABANS_REFRESH_TOKEN = REFRESH_TOKEN;
    }

    public static String getRefreshToken(Context context) {
        if (ABANS_REFRESH_TOKEN == null) {
            ABANS_REFRESH_TOKEN = AbansSaveSharedPreference.getSaveSharedPreference(context, CommonInterface.ABANS_REFRESH_TOKEN);
        }
        return ABANS_REFRESH_TOKEN;
    }

    public static void removeRefreshToken(Context context) {
        AbansSaveSharedPreference.removeSharedPreference(context, CommonInterface.ABANS_REFRESH_TOKEN);
    }

    public static String getREFRESHTOKEN() {
        return ABANS_REFRESH_TOKEN;
    }

    public static void setREFRESHTOKEN(String REFRESHTOKEN) {
        AbansTokenIdentifier.ABANS_REFRESH_TOKEN = REFRESHTOKEN;
    }

}
