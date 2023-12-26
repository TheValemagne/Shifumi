package com.example.shifumi.placeholder;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    public static List<PlaceholderItem> placeholderItemsMapper(Collection<WifiP2pDevice> peers) {
        List<WifiP2pDevice> wifiP2pDevices = new ArrayList<>(peers);
        List<PlaceholderItem> results = new ArrayList<>();

        for (WifiP2pDevice wifiP2pDevice : wifiP2pDevices) {
            results.add(createPlaceholderItem(wifiP2pDevice));
        }

        return results;
    }

    private static PlaceholderItem createPlaceholderItem(WifiP2pDevice wifiP2pDevice) {
        return new PlaceholderItem(wifiP2pDevice);
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String content;
        public final WifiP2pDevice wifiP2pDevice;

        public PlaceholderItem(WifiP2pDevice wifiP2pDevice) {
            this.wifiP2pDevice = wifiP2pDevice;
            this.content = wifiP2pDevice.deviceName;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}