package com.gadget_mart.security;

import android.content.Context;
import android.content.Intent;

import com.gadget_mart.LoginActivity;

public class LogoutProcess {

    public static void logout(Context context){

        com.gadget_mart.security.SaveSharedPreference.removeUsername(context);
        com.gadget_mart.security.SaveSharedPreference.removePassword(context);
        com.gadget_mart.security.SaveSharedPreference.removeUserId(context);
        com.gadget_mart.security.SaveSharedPreference.removeUserFullName(context);
        com.gadget_mart.security.TokenIdentifier.removeTOKEN(context);
        com.gadget_mart.security.TokenIdentifier.removeRefreshToken(context);
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

}
