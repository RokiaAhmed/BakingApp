package com.udacity.bakingapp.utills;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;


public class ConnectionDetector {

    private Context mContext;
    private static volatile ConnectionDetector sInstance;

    public ConnectionDetector(Context context) {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        mContext = context;
    }

    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (ConnectionDetector.class) {
                if (sInstance == null) {
                    sInstance = new ConnectionDetector(context);
                }
            }
        }
    }

    public static ConnectionDetector getInstance() {
        return sInstance;
    }

    /**
     * Checking for all possible internet providers
     **/

    //todo add context here instead of conrtext in constructor if you don't have any another functionn
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            } else {

                return false;
            }
        }
        //   Toast.makeText(mContext, mContext.getString(R.string.please_connect_to_internet), Toast.LENGTH_SHORT).show();
        return false;
    }

}