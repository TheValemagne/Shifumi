package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Ecouteur pour la découverte des pairs
 */
public class DiscoverPeersListener implements WifiP2pManager.ActionListener{
    private static final String TAG = "P2P discover";

    @Override
    public void onSuccess() {
        // Wi-Fi Direct discovery started successfully
        Log.d(TAG, "discoverPeers Success");
    }

    @Override
    public void onFailure(int reasonCode) {
        // Wi-Fi Direct discovery failed
        Log.d(TAG, "discoverPeers faillure " + reasonCode);
    }
}
