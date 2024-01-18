package com.example.shifumi.p2p.handler;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.example.shifumi.MainActivity;

import java.util.Objects;

public final class StateChangedActionHandler extends P2pHandler{

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

        if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
            Toast.makeText(mainActivity, "WiFi Direct activé", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mainActivity, "WiFi Direct déactivé", Toast.LENGTH_LONG).show();
        }
    }
}
