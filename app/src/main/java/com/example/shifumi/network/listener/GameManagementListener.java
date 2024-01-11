package com.example.shifumi.network.listener;

import com.example.shifumi.network.RequestNextRound;
import com.example.shifumi.network.Server;

import java.io.IOException;

public class GameManagementListener {
    private final Server server;

    public GameManagementListener(Server server) {
        this.server = server;
    }

    public void onNext() throws IOException {
        server.sendToAll(new RequestNextRound());
    }
}
