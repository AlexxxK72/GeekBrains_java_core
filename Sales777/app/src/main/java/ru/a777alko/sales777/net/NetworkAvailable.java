package ru.a777alko.sales777.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.a777alko.sales777.mvp.model.net.INetworkAvailable;


public class NetworkAvailable implements INetworkAvailable<Context> {

    @Override
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnected();
    }
}
