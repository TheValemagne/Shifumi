package com.example.shifumi.p2p;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.shifumi.MainActivity;

import java.io.Serializable;

public class PeerToPeerManager {
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private MainActivity mainActivity;

    private Server server;

    public PeerToPeerManager(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity mainActivity) {
        this.wifiP2pManager = manager;
        this.channel = channel;
        this.mainActivity = mainActivity;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.w("P2P", "Permission not granted " + this.getClass().getName());
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.NEARBY_WIFI_DEVICES, Manifest.permission.ACCESS_FINE_LOCATION}, mainActivity.PERMISSIONS_REQUEST_CODE);
            }
        } else  {
            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.w("P2P", "Permission not granted : " + this.getClass().getName());
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, mainActivity.PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void discoverPeers() {
        requestPermissions();
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Wi-Fi Direct discovery started successfully
                Log.d("P2P Manager", "discoverPeers Success");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Wi-Fi Direct discovery failed
                Log.d("P2P Manager", "discoverPeers faillure " + reasonCode);
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void connectToPeer(String ipAddress) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = ipAddress;

        wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Connection initiated successfully
            }

            @Override
            public void onFailure(int reason) {
                // Connection failed
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void createGroup() {
        wifiP2pManager.createGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Group created successfully
            }

            @Override
            public void onFailure(int reason) {
                // Group creation failed
            }
        });
    }

    public void startServer() {
        server = new Server();
        server.start();
    }

    public void closeServer() {
        server.closeConnection();
    }
}
