package com.example.shifumi.network;

import com.example.shifumi.MainActivity;
import com.example.shifumi.network.manager.ClientRoundManager;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Initiateur du client joueur
 */
public class InitClientRunnable implements Runnable {
    private final InetAddress groupOwnerAddress;
    private final MainActivity mainActivity;

    /**
     * Initiateur du client joueur
     *
     * @param groupOwnerAddress adresse de l'hôte de la partie
     * @param mainActivity activité principale
     */
    public InitClientRunnable(InetAddress groupOwnerAddress, MainActivity mainActivity) {
        this.groupOwnerAddress = groupOwnerAddress;
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        try {
            Client client = new Client(groupOwnerAddress,
                    new ClientRoundManager(mainActivity));
            client.start();

            mainActivity.setClient(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
