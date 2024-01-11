package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.network.RequestNextRound;
import com.example.shifumi.p2p.SendObjectHandler;

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
