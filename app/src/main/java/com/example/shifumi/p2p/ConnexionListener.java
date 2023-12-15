package com.example.shifumi.p2p;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class ConnexionListener implements WifiP2pManager.ConnectionInfoListener{
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        if (!info.groupFormed){
            return;
        }

        if(info.isGroupOwner) {
            //serveur
            Log.d("Serveur", "Addresse groupOwner " + info.groupOwnerAddress.toString());
        } else {
            // client
            Log.d("Client", "Address groupOwner " + info.groupOwnerAddress.toString());
        }
    }
}
