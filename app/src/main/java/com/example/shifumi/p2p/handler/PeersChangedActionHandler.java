package com.example.shifumi.p2p.handler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.shifumi.MainActivity;
import com.example.shifumi.p2p.listener.PeersListener;

import java.util.Objects;

/**
 * Gestionnaire WiFi direct lorsque la liste d'appareils disponibles à changer
 */
public final class PeersChangedActionHandler extends P2pHandler{
    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    private final WifiP2pManager.PeerListListener peerListListener;

    /**
     * Gestionnaire WiFi direct lorsque la liste d'appareils disponibles à changer
     *
     * @param mainActivity activité principale
     * @param wifiP2pManager gestionnaire de la connexion WiFi direct
     * @param channel cannal WiFi direct
     */
    public PeersChangedActionHandler(MainActivity mainActivity, WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel) {
        super(mainActivity);

        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.peerListListener = new PeersListener(mainActivity);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void handle(Intent intent) {
        if (!(Objects.equals(intent.getAction(), WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION))) {
            super.handle(intent);
            return;
        }

        if (wifiP2pManager == null) {
            return;
        }

        // lancement du scan d'appareils disponibles
        wifiP2pManager.requestPeers(channel, peerListListener);
    }
}
