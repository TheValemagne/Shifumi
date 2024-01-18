package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.network.request.RequestNextRound;
import com.example.shifumi.p2p.SendObjectHandler;

/**
 * Ecouteur tour suivant
 */
public class NextButtonListener implements View.OnClickListener {
    private final SendObjectHandler sendObjectHandler;

    public NextButtonListener(SendObjectHandler sendObjectHandler) {
        this.sendObjectHandler = sendObjectHandler;
    }

    @Override
    public void onClick(View v) {
        sendObjectHandler.send(new RequestNextRound());
    }
}
