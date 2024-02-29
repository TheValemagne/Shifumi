package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Ecouteur de connexion au group wifi direct
 */
public class DisconnectListener implements WifiP2pManager.GroupInfoListener {
    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;

    /**
     * Ecouteur de connexion au group wifi direct
     *
     * @param wifiP2pManager gestionnaire de la connexion WiFi direct
     * @param channel cannal WiFi direct
     */
    public DisconnectListener(WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel) {
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
    }

    @Override
    public void onGroupInfoAvailable(WifiP2pGroup group) {
        if (group == null || wifiP2pManager == null || channel == null) {
            return;
        }

        // demande de suppression du groupe WiFi direct
        wifiP2pManager.removeGroup(channel, new RemoveGroupListener());
    }
}
