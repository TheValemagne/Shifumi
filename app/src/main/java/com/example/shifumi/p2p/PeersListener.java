package com.example.shifumi.p2p;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.WifiDevicesFragment;
import com.example.shifumi.placeholder.PlaceholderContent;

public class PeersListener implements WifiP2pManager.PeerListListener {
    private MainActivity mainActivity;
    private PeerToPeerManager peerToPeerManager;

    public PeersListener(MainActivity mainActivity, PeerToPeerManager peerToPeerManager) {
        this.mainActivity = mainActivity;
        this.peerToPeerManager = peerToPeerManager;
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        // Handle the list of available peers
        System.out.println("TEST " + peers.getDeviceList());

        if(!peers.equals(PlaceholderContent.ITEMS)) {
            WifiDevicesFragment fragment = (WifiDevicesFragment) mainActivity.getSupportFragmentManager()
                    .findFragmentById(R.id.main_frame);

            fragment.updateData(peers.getDeviceList());
        }

        // TODO empty list -> refresh button with discorverPeers

        if (peers.getDeviceList().size() > 0) {
            //PlaceholderContent.updateItems(peers);
            // Connect to the first available peer (you might want to show a list to the user)
            //String peer = peers.getDeviceList().iterator().next().toString();
            //peerToPeerManager.connectToPeer(peer);
        }
    }
}
