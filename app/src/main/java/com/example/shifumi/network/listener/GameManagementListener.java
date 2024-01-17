package com.example.shifumi.network.listener;

import com.example.shifumi.network.request.RequestEndgame;
import com.example.shifumi.network.request.RequestNextRound;
import com.example.shifumi.network.Server;

public class GameManagementListener {
    private final Server server;

    public GameManagementListener(Server server) {
        this.server = server;
    }

    public void onNext() {
        server.sendToAll(new RequestNextRound());
    }

    public void onEnd() {
        server.sendToAll(new RequestEndgame());
    }
}
