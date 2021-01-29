package com.gadget_mart.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pradeep_r on 9/20/2018.
 */

public class ConnectionDetector {

    private static Context _context;
    private static ConnectionDetector con;

    private ConnectionDetector(){}

    public static synchronized ConnectionDetector getConnectionInstance(Context context){
        if (con == null){
            con = new ConnectionDetector();
        }
        _context = context;
        return con;
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;

    }

}

