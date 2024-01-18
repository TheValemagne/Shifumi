package com.example.shifumi.fragment.placeholder;

import android.net.wifi.p2p.WifiP2pDevice;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Conteneur d'information pour appareil wifi
 */
public final class WifiDeviceContent {

    /**
     * Convertisseur de WifiP2pDevice en WifiDeviceItem
     *
     * @param peers la liste d'appareils wifi disponibles
     * @return liste de WifiDeviceItem
     */
    public static List<WifiDeviceItem> placeholderItemsMapper(Collection<WifiP2pDevice> peers) {
        List<WifiP2pDevice> wifiP2pDevices = new ArrayList<>(peers);
        List<WifiDeviceItem> results = new ArrayList<>();

        for (WifiP2pDevice wifiP2pDevice : wifiP2pDevices) {
            results.add(new WifiDeviceItem(wifiP2pDevice));
        }

        return results;
    }

    public static class WifiDeviceItem {
        public final String content;
        public final WifiP2pDevice wifiP2pDevice;

        public WifiDeviceItem(WifiP2pDevice wifiP2pDevice) {
            this.wifiP2pDevice = wifiP2pDevice;
            this.content = wifiP2pDevice.deviceName;
        }

        @NonNull
        @Override
        public String toString() {
            return content;
        }
    }
}