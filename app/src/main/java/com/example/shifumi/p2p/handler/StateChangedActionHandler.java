package com.example.shifumi.p2p.handler;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;

import java.util.Objects;

/**
 * Gestionnaire WiFi direct lorsque le Wi-Fi peer to peer est activé ou désactivé
 */
public final class StateChangedActionHandler extends P2pHandler{

    /**
     * Gestionnaire WiFi direct lorsque le Wi-Fi peer to peer est activé ou désactivé
     *
     * @param mainActivity activité principale
     */
    public StateChangedActionHandler(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void handle(Intent intent) {
        if(!(Objects.equals(intent.getAction(), WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION))) {
            super.handle(intent);
            return;
        }

        int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

        // affichage de l'état du WiFi direct
        if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
            Toast.makeText(mainActivity, R.string.wifiDirectIsOn, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mainActivity, R.string.wifiDirectIsOff, Toast.LENGTH_LONG).show();
        }
    }
}
