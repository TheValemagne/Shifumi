package com.example.shifumi.placeholder;

import android.net.wifi.p2p.WifiP2pDevice;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WifiDeviceContent {

    public static List<WifiDeviceItem> placeholderItemsMapper(Collection<WifiP2pDevice> peers) {
        List<WifiP2pDevice> wifiP2pDevices = new ArrayList<>(peers);
        List<WifiDeviceItem> results = new ArrayList<>();

        for (WifiP2pDevice wifiP2pDevice : wifiP2pDevices) {
            results.add(createPlaceholderItem(wifiP2pDevice));
        }

        return results;
    }

    private static WifiDeviceItem createPlaceholderItem(WifiP2pDevice wifiP2pDevice) {
        return new WifiDeviceItem(wifiP2pDevice);
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