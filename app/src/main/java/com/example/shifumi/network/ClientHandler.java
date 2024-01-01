package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;

import java.io.IOException;
import java.net.Socket;

public final class ClientHandler extends ClientBase{
    private static final String TAG = "ClientHandler";
    private final int clientId;
    private final Server server;

    public ClientHandler(int clientId, Socket socket, Server server, Game game) throws IOException {
        super(game, socket);

        this.clientId = clientId;
        this.server = server;
    }

    @Override
    public void run() {
        super.run();

        while (!this.isInterrupted()){
            try {
                Object response = this.incomingFlow.readObject();

                if (response instanceof Choice) {
                    Log.d(TAG, "Choix reçu : " + response);

                    server.setChoice(clientId, (Choice) response);

                    if(server.areAllChoicesSet()) {
                        // TODO do something with server if both choices are set
                        server.getClient(0).setOpponentChoice(server.getChoice(1));
                        server.getClient(1).setOpponentChoice(server.getChoice(0));
                    }

                    synchronized (opponentChoiceLock) {
                        while (getOpponentChoice().equals(Choice.UNSET)) {
                            opponentChoiceLock.wait();
                        }

                        this.outgoingFlow.writeObject(getOpponentChoice());
                    }
                }

            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
