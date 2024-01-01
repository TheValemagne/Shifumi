package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public final class Client extends ClientBase {
    private static final String TAG = "Client";
    private final InetAddress groupOwnerAddress;

    public Client(InetAddress groupOwnerAddress, Game game) throws IOException {
        super(game, new Socket(groupOwnerAddress.getHostAddress(), Server.port));
        this.groupOwnerAddress = groupOwnerAddress;
    }

    @Override
    public void run() {
        super.run();

        Log.d(TAG, String.format("Client connecté à l'adresse : %s, adresse client : %s", groupOwnerAddress, socket.getLocalAddress()));

        while (!this.isInterrupted()) {
            try {
                // TODO game management
                synchronized (ownChoiceLock) {
                    while (getOwnChoice().equals(Choice.UNSET)) {
                        ownChoiceLock.wait();
                    }
                    this.outgoingFlow.writeObject(getOwnChoice());
                }

                Object response = this.incomingFlow.readObject(); // wait for server response

                if (response instanceof Choice) {
                    setOpponentChoice((Choice) response);
                }
                // TODO update UI + score

            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Log.d(TAG, "Client déconnecté");
    }
}
