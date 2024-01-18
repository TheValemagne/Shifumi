package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Ecouteur de connexion au group wifi direct
 */
public class DisconnectListener implements WifiP2pManager.GroupInfoListener {
    private final static String TAG = "Disconnect WIFI direct";
    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    public DisconnectListener(WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel) {
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
    }

    @Override
    public void onGroupInfoAvailable(WifiP2pGroup group) {
        if (group != null && wifiP2pManager != null && channel != null) {
            wifiP2pManager.removeGroup(channel, new WifiP2pManager.ActionListener() {

                @Override
                public void onSuccess() {
                    Log.d(TAG, "removeGroup onSuccess -");
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "removeGroup onFailure -" + reason);
                }
            });
        }
    }
}
