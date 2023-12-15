package com.example.shifumi.p2p;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private PeerToPeerManager peerToPeerManager; // Assuming you have a reference to your PeerToPeerManager

    public WifiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       PeerToPeerManager peerToPeerManager) {
        super();
        this.wifiP2pManager = manager;
        this.channel = channel;
        this.peerToPeerManager = peerToPeerManager;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wi-Fi P2P mode is enabled or disabled
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Log.d("Connexion", "Connected");
                // Wi-Fi P2P is enabled
            } else {
                Log.d("Connexion", "Disconnected");
                // Wi-Fi P2P is not enabled
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.d("Connexion", "peers changed");
            // Request available peers from the Wi-Fi P2P manager
            if (wifiP2pManager != null) {
                wifiP2pManager.requestPeers(channel, peerListListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d("Connexion", "Connection_changed_action");
            // Respond to new connection or disconnections
            if (wifiP2pManager == null) {
                return;
            }

            WifiP2pInfo wifiP2pInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);
            if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                // This device is the group owner (server)
                peerToPeerManager.startServer();

            } else if (wifiP2pInfo.groupFormed) {
                // This device is a client
                peerToPeerManager.connectToPeer(wifiP2pInfo.groupOwnerAddress.getHostAddress());
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.d("Connexion", "this device");
            // Respond to this device's wifi state changing
            // For example, obtain the device's details
            WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
        }
    }

    // Listener to handle peer list changes
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            // Handle the list of available peers
            if (peers.getDeviceList().size() > 0) {
                // Connect to the first available peer (you might want to show a list to the user)
                String peer = peers.getDeviceList().iterator().next().toString();
                peerToPeerManager.connectToPeer(peer);
            }
        }
    };
}