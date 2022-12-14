package com.example.szymonapp005;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Networking {
    public static boolean isWifiOn(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d("xxx", "isInternet: " + netInfoWifi);

        if (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting())
            return true;
        else
            return false;
    }
}
