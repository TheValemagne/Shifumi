package com.example.shifumi.p2p;

import com.example.shifumi.game.Game;

import java.net.InetAddress;

public class Client extends Thread {
    private final int port = 8888;
    private final InetAddress groupOwnerAddress;
    private final Game game;

    public Client(InetAddress groupOwnerAddress, Game game) {
        this.groupOwnerAddress = groupOwnerAddress;
        this.game = game;
    }

    @Override
    public void run() {
        super.run();
    }
}
