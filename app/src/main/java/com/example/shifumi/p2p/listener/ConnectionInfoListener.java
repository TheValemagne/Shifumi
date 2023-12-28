package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.shifumi.MainActivity;
import com.example.shifumi.p2p.PeerToPeerManager;

public class ConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener{
    private final MainActivity mainActivity;
    private final PeerToPeerManager peerToPeerManager;

    public ConnectionInfoListener(MainActivity mainActivity, PeerToPeerManager peerToPeerManager) {
        this.mainActivity = mainActivity;
        this.peerToPeerManager = peerToPeerManager;
    }
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        if (!info.groupFormed){
            return;
        }

        if(info.isGroupOwner) {
            //serveur
            Log.d("Serveur", "Addresse groupOwner " + info.groupOwnerAddress.toString());
            peerToPeerManager.startServer(mainActivity.getGame());
        } else {
            // client
            Log.d("Client", "Address groupOwner " + info.groupOwnerAddress.toString());
        }

        Toast.makeText(mainActivity, "Connexion réussie", Toast.LENGTH_LONG).show();
    }
}
