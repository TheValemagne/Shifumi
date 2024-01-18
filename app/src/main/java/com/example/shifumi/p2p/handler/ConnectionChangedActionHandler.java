package com.example.shifumi.p2p.handler;

import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.shifumi.MainActivity;
import com.example.shifumi.p2p.PeerToPeerManager;
import com.example.shifumi.p2p.listener.ConnectionInfoListener;

import java.util.Objects;

public final class ConnectionChangedActionHandler extends P2pHandler{
    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    private final PeerToPeerManager peerToPeerManager;

    public ConnectionChangedActionHandler(MainActivity mainActivity, WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel) {
        super(mainActivity);

        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.peerToPeerManager = mainActivity.getPeerToPeerManager();
    }

    @Override
    public void handle(Intent intent) {
        if(!(Objects.equals(intent.getAction(), WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION))) {
            super.handle(intent);
            return;
        }

        // Respond to new connection or disconnections
        if (wifiP2pManager == null) {
            return;
        }

        NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
        assert networkInfo != null;

        if (!networkInfo.isConnected()){
            return;
        }

        this.wifiP2pManager.requestConnectionInfo(channel, new ConnectionInfoListener(mainActivity, peerToPeerManager));
    }
}
