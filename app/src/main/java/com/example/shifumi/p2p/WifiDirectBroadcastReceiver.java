package com.example.shifumi.p2p;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.shifumi.MainActivity;
import com.example.shifumi.p2p.listener.ConnectionInfoListener;
import com.example.shifumi.p2p.listener.PeersListener;

public final class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "WifiDirectBroadcastReceiver";
    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    private final MainActivity mainActivity;
    private final PeerToPeerManager peerToPeerManager;
    private final WifiP2pManager.PeerListListener peerListListener;

    public WifiDirectBroadcastReceiver(WifiP2pManager manager,
                                       WifiP2pManager.Channel channel,
                                       PeerToPeerManager peerToPeerManager,
                                       MainActivity mainActivity) {
        super();
        this.wifiP2pManager = manager;
        this.channel = channel;
        this.peerToPeerManager = peerToPeerManager;
        this.mainActivity = mainActivity;

        this.peerListListener = new PeersListener(mainActivity);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action == null) {
            return;
        }

        switch (action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION:
                onStateChangedAction(intent);
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION:
                Log.d(TAG, "peers changed");
                // Request available peers from the Wi-Fi P2P manager
                if (wifiP2pManager != null) {
                    wifiP2pManager.requestPeers(channel, peerListListener);
                }
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                onConnectionChangedAction(intent);
                break;
        }
    }

    private void onConnectionChangedAction(Intent intent) {
        Log.d(TAG, "Connection_changed_action");
        // Respond to new connection or disconnections
        if (wifiP2pManager == null) {
            return;
        }

        NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
        assert networkInfo != null;

        if (networkInfo.isConnected())
            this.wifiP2pManager.requestConnectionInfo(channel, new ConnectionInfoListener(mainActivity, peerToPeerManager));
    }

    private void onStateChangedAction(Intent intent) {
        // Determine if Wi-Fi P2P mode is enabled or disabled
        int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

        if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
            Toast.makeText(mainActivity, "WiFi Direct activé", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mainActivity, "WiFi Direct déactivé", Toast.LENGTH_LONG).show();
        }
    }
}