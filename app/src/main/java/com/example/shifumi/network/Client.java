package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.ClientListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public final class Client extends ClientBase {
    private static final String TAG = "Client";
    private final InetAddress groupOwnerAddress;
    private final ClientListener clientResponseListener;

    public Client(InetAddress groupOwnerAddress,
                  ClientListener clientResponseListener) throws IOException {
        super(new Socket(groupOwnerAddress.getHostAddress(), Server.port));
        this.groupOwnerAddress = groupOwnerAddress;
        this.clientResponseListener = clientResponseListener;
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
                    // TODO update UI + score
                    clientResponseListener.onReceive(getOpponentChoice());
                }

                // TODO Next or Endgame

            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Log.d(TAG, "Client déconnecté");
    }
}
