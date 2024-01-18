package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.network.Client;
import com.example.shifumi.network.request.RequestNextRound;

/**
 * Ecouteur tour suivant
 */
public class NextButtonListener implements View.OnClickListener {
    private final Client client;

    public NextButtonListener(Client client) {
        this.client = client;
    }

    @Override
    public void onClick(View v) {
        client.send(new RequestNextRound());
    }
}
