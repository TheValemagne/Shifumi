package com.example.shifumi.p2p;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.shifumi.MainActivity;
import com.example.shifumi.p2p.handler.ConnectionChangedActionHandler;
import com.example.shifumi.p2p.handler.P2pHandler;
import com.example.shifumi.p2p.handler.PeersChangedActionHandler;
import com.example.shifumi.p2p.handler.StateChangedActionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private final List<P2pHandler> handlers;

    public WifiDirectBroadcastReceiver(WifiP2pManager wifiP2pManager,
                                       WifiP2pManager.Channel channel,
                                       MainActivity mainActivity) {
        super();

        this.handlers = new ArrayList<>(Arrays.asList(
                new StateChangedActionHandler(mainActivity),
                new PeersChangedActionHandler(mainActivity, wifiP2pManager, channel),
                new ConnectionChangedActionHandler(mainActivity, wifiP2pManager, channel)
        ));

        for (int index = 0; index < handlers.size() - 1; index++) {
            handlers.get(index).setNextHandler(handlers.get(index+1));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == null) {
            return;
        }

        handlers.get(0).handle(intent);
    }
}