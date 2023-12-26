package com.example.shifumi;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.shifumi.p2p.PeerToPeerManager;

public class StartButtonListener implements Button.OnClickListener {
    private PeerToPeerManager peerToPeerManager;
    private MainActivity mainActivity;

    public StartButtonListener(MainActivity mainActivity, PeerToPeerManager peerToPeerManager) {
        this.mainActivity = mainActivity;
        this.peerToPeerManager = peerToPeerManager;
    }

    @Override
    public void onClick(View v) {
        peerToPeerManager.discoverPeers();

        Fragment deviceList = new WifiDevicesFragment();

        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, deviceList)
                .commit();
    }
}
