package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

import androidx.fragment.app.Fragment;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.WifiDevicesFragment;

/**
 * Ecouteur de réception des pairs disponibles
 */
public class PeersListener implements WifiP2pManager.PeerListListener {
    private final MainActivity mainActivity;

    public PeersListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        // récupération du fragment actuel
        Fragment fragment = mainActivity.getSupportFragmentManager()
                .findFragmentById(R.id.main_frame);

        if (!(fragment instanceof WifiDevicesFragment)) {
            return;
        }

        WifiDevicesFragment wifiDevicesFragment = (WifiDevicesFragment) fragment;

        // actualisation de la liste d'appareils disponibles
        if(!peers.getDeviceList().equals(wifiDevicesFragment.getPeers())) {
            wifiDevicesFragment.updateData(peers.getDeviceList());
        }

    }
}
