package com.example.shifumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shifumi.fragment.StartScreenFragment;
import com.example.shifumi.p2p.PeerToPeerManager;
import com.example.shifumi.p2p.WifiDirectBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    private WifiDirectBroadcastReceiver wifiReceiver;

    public PeerToPeerManager getPeerToPeerManager() {
        return peerToPeerManager;
    }

    private PeerToPeerManager peerToPeerManager;
    private IntentFilter intentFilter;
    public static final int PERMISSIONS_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPeerToPeer();
        showFragment();
    }

    private void showFragment() {
        if(this.getSupportFragmentManager().findFragmentById(R.id.main_frame) != null) {
            return;
        }

        Fragment startScreen = new StartScreenFragment();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, startScreen)
                .commit();
    }

    private void initPeerToPeer() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.EXTRA_WIFI_P2P_INFO);

        WifiP2pManager wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel wifiChannel = wifiP2pManager.initialize(this, getMainLooper(), null);
        peerToPeerManager = new PeerToPeerManager(wifiP2pManager, wifiChannel, this);

        wifiReceiver = new WifiDirectBroadcastReceiver(wifiP2pManager, wifiChannel, peerToPeerManager, this);
        registerReceiver(wifiReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wifiReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiReceiver);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // Handle the result of the permission request
            // For simplicity, you can assume that the permissions are granted, but in a real app, you should check each permission.
            Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
        }
    }
}