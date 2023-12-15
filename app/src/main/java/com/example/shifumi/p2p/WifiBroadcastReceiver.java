package com.example.shifumi.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private final WifiP2pManager wifiManager;
    private final WifiP2pManager.Channel wifiChannel;

    public WifiBroadcastReceiver(WifiP2pManager wifiManager, WifiP2pManager.Channel wifiChannel) {
        this.wifiManager = wifiManager;
        this.wifiChannel = wifiChannel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action == null) {
            return;
        }

        switch (action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION:
                int extra = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

                if (extra == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    // WiFi p2p vient d'être activé
                    Log.d("Connexion", "Connected");
                } else {
                    // WiFi p2p vient d'être déactivé
                }

                break;

            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION:
                Log.d("Connexion", "peers changed");
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                // le réseau local a changé
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                assert networkInfo != null;
                Log.d("Connexion", "Connection_changed_action");
                if (networkInfo.isConnected()){
                    wifiManager.requestConnectionInfo(wifiChannel, new ConnexionListener());
                }
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:
                NetworkInfo nInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                assert nInfo != null;
                Log.d("Connexion", "this device");
                if (nInfo.isConnected()){
                    wifiManager.requestConnectionInfo(wifiChannel, new ConnexionListener());
                }
                break;
            default:
                Log.d("Connexion", action);
        }

    }


}
