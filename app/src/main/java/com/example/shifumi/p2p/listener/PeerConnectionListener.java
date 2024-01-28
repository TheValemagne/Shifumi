package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;

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
        Toast.makeText(mainActivity, mainActivity.getBaseContext().getString(R.string.connectionFailure), Toast.LENGTH_LONG).show();
    }
}
