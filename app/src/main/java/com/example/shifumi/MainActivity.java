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
import com.example.shifumi.game.Game;
import com.example.shifumi.network.Client;
import com.example.shifumi.p2p.PeerToPeerManager;
import com.example.shifumi.p2p.WifiDirectBroadcastReceiver;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    public static final int PERMISSIONS_REQUEST_CODE = 1001;
    private WifiDirectBroadcastReceiver wifiReceiver;
    private PeerToPeerManager peerToPeerManager;
    public PeerToPeerManager getPeerToPeerManager() {
        return peerToPeerManager;
    }
    private Game game;
    public Game getGame() {
        return game;
    }

    public String getScoreMsg() {
        return MessageFormat.format("Score : {0} - {1}", game.getPlayerScore(), game.getOpponentScore());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.EXTRA_WIFI_P2P_INFO);

        initWifiDirectConnection();
        registerReceiver(wifiReceiver, intentFilter);
        showFragment();
    }

    private void showFragment() {
        if (this.getSupportFragmentManager().findFragmentById(R.id.main_frame) != null) {
            return;
        }

        Fragment startScreen = new StartScreenFragment();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, startScreen)
                .commit();
    }

    private void initWifiDirectConnection() {
        WifiP2pManager wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel wifiChannel = wifiP2pManager.initialize(this, getMainLooper(), null);
        peerToPeerManager = new PeerToPeerManager(wifiP2pManager, wifiChannel, this);

        wifiReceiver = new WifiDirectBroadcastReceiver(wifiP2pManager, wifiChannel, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wifiReceiver, intentFilter);
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