package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.shifumi.MainActivity;

/**
 * Ecouteur de connexion au pair
 */
public class PeerConnectionListener implements WifiP2pManager.ActionListener {
    private final MainActivity mainActivity;

    public PeerConnectionListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onSuccess() {
        // Connection initiated successfully
    }

    @Override
    public void onFailure(int reason) {
        // Connection failed
        Toast.makeText(mainActivity, "Erreur de connexion", Toast.LENGTH_LONG).show();
        Log.d("ERREUR", "Test " + reason);
    }
}
