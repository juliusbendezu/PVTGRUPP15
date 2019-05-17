package com.dsv2019.pvt15.prepapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class InternetConnection {

    // check weather internetconnection is available or not.
    public static boolean checkConnection(@NonNull Context context){
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
