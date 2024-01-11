package com.example.shifumi.fragment.listener;

import android.view.View;
import android.widget.Button;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.WifiDevicesFragment;

public class StartButtonListener implements Button.OnClickListener {
    private final MainActivity mainActivity;

    public StartButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.getPeerToPeerManager().discoverPeers();

        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new WifiDevicesFragment())
                .commit();
    }
}
