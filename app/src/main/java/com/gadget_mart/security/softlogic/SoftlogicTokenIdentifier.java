package com.gadget_mart.security.softlogic;

import android.content.Context;

import com.gadget_mart.security.CommonInterface;
import com.gadget_mart.security.abans.AbansSaveSharedPreference;

public class SoftlogicTokenIdentifier implements java.io.Serializable {

    private static String SOFTLOGIC_TOKEN;
    private static String SOFTLOGIC_REFRESH_TOKEN;

    public SoftlogicTokenIdentifier() {
    }

    public static void setTOKEN(String TOKEN, Context context) {
        AbansSaveSharedPreference.setSaveSharedPreference(context, CommonInterface.SOFTLOGIC_TOKEN, TOKEN);
        SoftlogicTokenIdentifier.SOFTLOGIC_TOKEN = TOKEN;
    }

    public static String getTOKEN(Context context) {
        if (SOFTLOGIC_TOKEN == null) {
            SOFTLOGIC_TOKEN = AbansSaveSharedPreference.getSaveSharedPreference(context, CommonInterface.SOFTLOGIC_TOKEN);
        }
        return SOFTLOGIC_TOKEN;
    }

    public static void removeTOKEN(Context context) {
        AbansSaveSharedPreference.removeSharedPreference(context, CommonInterface.SOFTLOGIC_TOKEN);
    }

    public static void setRefreshToken(String REFRESH_TOKEN, Context context) {
        AbansSaveSharedPreference.setSaveSharedPreference(context, CommonInterface.SOFTLOGIC_REFRESH_TOKEN, SOFTLOGIC_TOKEN);
        SoftlogicTokenIdentifier.SOFTLOGIC_REFRESH_TOKEN = REFRESH_TOKEN;
    }

    public static String getRefreshToken(Context context) {
        if (SOFTLOGIC_REFRESH_TOKEN == null) {
            SOFTLOGIC_REFRESH_TOKEN = AbansSaveSharedPreference.getSaveSharedPreference(context, CommonInterface.SOFTLOGIC_REFRESH_TOKEN);
        }
        return SOFTLOGIC_REFRESH_TOKEN;
    }

    public static void removeRefreshToken(Context context) {
        AbansSaveSharedPreference.removeSharedPreference(context, CommonInterface.SOFTLOGIC_TOKEN);
    }

    public static String getREFRESHTOKEN() {
        return SOFTLOGIC_REFRESH_TOKEN;
    }

    public static void setREFRESHTOKEN(String REFRESHTOKEN) {
        SoftlogicTokenIdentifier.SOFTLOGIC_REFRESH_TOKEN = REFRESHTOKEN;
    }

}
