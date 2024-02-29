package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Ecouteur de supression de group WiFi direct
 */
public class RemoveGroupListener implements WifiP2pManager.ActionListener{
    private final static String TAG = "Disconnect WIFI direct";

    @Override
    public void onSuccess() {
        Log.d(TAG, "removeGroup onSuccess -");
    }

    @Override
    public void onFailure(int reason) {
        Log.d(TAG, "removeGroup onFailure -" + reason);
    }
}
