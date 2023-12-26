package com.example.shifumi;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.shifumi.fragment.WifiDevicesFragment;
import com.example.shifumi.p2p.PeerToPeerManager;

public class StartButtonListener implements Button.OnClickListener {
    private final MainActivity mainActivity;

    public StartButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.getPeerToPeerManager().discoverPeers();

        Fragment deviceList = new WifiDevicesFragment();

        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, deviceList)
                .commit();
    }
}
