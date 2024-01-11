package com.example.shifumi.network.listener;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.Server;

public class ChoiceUpdateListener implements ClientHandlerListener {
    private final int clientId;
    private final Server server;

    public ChoiceUpdateListener(int clientId, Server server) {
        this.clientId = clientId;
        this.server = server;
    }

    @Override
    public void onReceive(Choice choice) {
        server.setChoice(clientId, choice); // update server state

        if(server.areAllChoicesSet()) { // all players have played this round
            // Update both clients with opponent choice
            server.getClient(0).setOpponentChoice(server.getChoice(1));
            server.getClient(1).setOpponentChoice(server.getChoice(0));
            server.resetChoices();
        }
    }
}
