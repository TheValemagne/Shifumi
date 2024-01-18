package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.network.request.RequestEndgame;
import com.example.shifumi.p2p.SendObjectHandler;

/**
 * Ecouteur de fin de jeu
 */
public class EndgameListener implements View.OnClickListener {
    private final SendObjectHandler sendObjectHandler;

    public EndgameListener(SendObjectHandler sendObjectHandler) {
        this.sendObjectHandler = sendObjectHandler;
    }

    @Override
    public void onClick(View v) {
        sendObjectHandler.send(new RequestEndgame());
    }
}
