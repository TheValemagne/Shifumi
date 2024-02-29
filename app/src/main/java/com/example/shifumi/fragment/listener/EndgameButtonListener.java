package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.network.Client;
import com.example.shifumi.network.request.RequestEndgame;

/**
 * Ecouteur de fin de jeu
 */
public class EndgameButtonListener implements View.OnClickListener {
    private final Client client;

    /**
     * Ecouteur de fin de jeu
     *
     * @param client client joueur
     */
    public EndgameButtonListener(Client client) {
        this.client = client;
    }

    @Override
    public void onClick(View v) {
        client.send(new RequestEndgame());
    }
}
