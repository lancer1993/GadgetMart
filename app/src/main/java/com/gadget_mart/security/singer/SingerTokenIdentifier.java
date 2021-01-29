package com.gadget_mart.security.singer;

import android.content.Context;

import com.gadget_mart.security.CommonInterface;
import com.gadget_mart.security.abans.AbansSaveSharedPreference;

public class SingerTokenIdentifier implements java.io.Serializable {

    private static String SINGER_TOKEN;
    private static String SINGER_REFRESH_TOKEN;

    public SingerTokenIdentifier() {
    }

    public static void setTOKEN(String TOKEN, Context context) {
        AbansSaveSharedPreference.setSaveSharedPreference(context, CommonInterface.ABANS_TOKEN, TOKEN);
        SingerTokenIdentifier.SINGER_TOKEN = TOKEN;
    }

    public static String getTOKEN(Context context) {
        if (SINGER_TOKEN == null) {
            SINGER_TOKEN = AbansSaveSharedPreference.getSaveSharedPreference(context, CommonInterface.ABANS_TOKEN);
        }
        return SINGER_TOKEN;
    }

    public static void removeTOKEN(Context context) {
        AbansSaveSharedPreference.removeSharedPreference(context, CommonInterface.ABANS_TOKEN);
    }

    public static void setRefreshToken(String REFRESH_TOKEN, Context context) {
        AbansSaveSharedPreference.setSaveSharedPreference(context, CommonInterface.ABANS_TOKEN, SINGER_TOKEN);
        SingerTokenIdentifier.SINGER_REFRESH_TOKEN = REFRESH_TOKEN;
    }

    public static String getRefreshToken(Context context) {
        if (SINGER_REFRESH_TOKEN == null) {
            SINGER_REFRESH_TOKEN = AbansSaveSharedPreference.getSaveSharedPreference(context, CommonInterface.ABANS_REFRESH_TOKEN);
        }
        return SINGER_REFRESH_TOKEN;
    }

    public static void removeRefreshToken(Context context) {
        AbansSaveSharedPreference.removeSharedPreference(context, CommonInterface.ABANS_REFRESH_TOKEN);
    }

    public static String getREFRESHTOKEN() {
        return SINGER_REFRESH_TOKEN;
    }

    public static void setREFRESHTOKEN(String REFRESHTOKEN) {
        SingerTokenIdentifier.SINGER_REFRESH_TOKEN = REFRESHTOKEN;
    }

}
