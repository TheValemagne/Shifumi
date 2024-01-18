package com.example.shifumi.network;

import com.example.shifumi.MainActivity;
import com.example.shifumi.network.listener.ClientRoundListener;

import java.io.IOException;
import java.net.InetAddress;

public class InitClientRunnable implements Runnable {
    private final InetAddress groupOwnerAddress;
    private final MainActivity mainActivity;

    public InitClientRunnable(InetAddress groupOwnerAddress, MainActivity mainActivity) {
        this.groupOwnerAddress = groupOwnerAddress;
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        try {
            Client client = new Client(groupOwnerAddress,
                    new ClientRoundListener(mainActivity));
            client.start();

            mainActivity.setClient(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
