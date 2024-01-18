package com.example.shifumi.p2p;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.shifumi.MainActivity;
import com.example.shifumi.network.Server;
import com.example.shifumi.p2p.listener.DisconnectListener;
import com.example.shifumi.p2p.listener.DiscoverPeersListener;
import com.example.shifumi.p2p.listener.PeerConnectionListener;

import java.io.IOException;

/**
 * Gestionnaire des échanges wifi direct
 */
public final class PeerToPeerManager {
    private final static String TAG = "P2P Manager";
    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    private final MainActivity mainActivity;

    private Server server;

    public PeerToPeerManager(WifiP2pManager manager,
                             WifiP2pManager.Channel channel,
                             MainActivity mainActivity) {
        this.wifiP2pManager = manager;
        this.channel = channel;
        this.mainActivity = mainActivity;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.w(TAG, "Permission not granted " + this.getClass().getName());
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.NEARBY_WIFI_DEVICES, Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.PERMISSIONS_REQUEST_CODE);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "Permission not granted : " + this.getClass().getName());
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    /**
     * Lancement de la découverte des pairs disponibles
     */
    @SuppressLint("MissingPermission")
    public void discoverPeers() {
        requestPermissions();
        wifiP2pManager.discoverPeers(channel, new DiscoverPeersListener());
    }

    /**
     * Connexion à une pair en wifi direct
     *
     * @param ipAddress adresse de l'appareil à connecter
     */
    @SuppressLint("MissingPermission")
    public void connectToPeer(String ipAddress) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = ipAddress;
        config.wps.setup = WpsInfo.PBC;

        wifiP2pManager.connect(channel, config, new PeerConnectionListener(mainActivity));
    }

    /**
     * Déconnexion du group wifi direct
     */
    public void disconnect() {
        if (wifiP2pManager != null && channel != null) {
            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            wifiP2pManager.requestGroupInfo(channel, new DisconnectListener(wifiP2pManager, channel));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                channel.close();
            }

            closeClient();
            closeServer();
        }
    }

    /**
     * Fermeture du client joueur
     */
    private void closeClient() {
        if(mainActivity.getClient() == null) {
            return;
        }

        try {
            mainActivity.getClient().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lancement du serveur
     */
    public void startServer() {
        server = new Server();
        server.start();
    }

    /**
     * Fermeture du serveur
     */
    private void closeServer() {
        if(server == null) {
            return;
        }

        server.closeConnection();
    }
}
