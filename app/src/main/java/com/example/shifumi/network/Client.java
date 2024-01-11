package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.ClientListener;
import com.example.shifumi.network.listener.ClientRoundListener;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public final class Client extends ClientBase {
    private static final String TAG = "Client";
    private final InetAddress groupOwnerAddress;
    private final ClientListener clientResponseListener;
    private final ClientRoundListener clientRoundListener;

    public Client(InetAddress groupOwnerAddress,
                  ClientListener clientResponseListener,
                  ClientRoundListener clientRoundListener) throws IOException {
        super(new Socket(groupOwnerAddress.getHostAddress(), Server.port));

        this.groupOwnerAddress = groupOwnerAddress;
        this.clientResponseListener = clientResponseListener;
        this.clientRoundListener = clientRoundListener;
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
                        Log.d(TAG, "Is waiting");
                        ownChoiceLock.wait();
                    }
                    Log.d(TAG, "Choix joueur actuel");
                    this.outgoingFlow.writeObject(getOwnChoice());
                    Log.d(TAG, "Choix envoyé");
                }

                Object response = this.incomingFlow.readObject(); // wait for server response

                if (response instanceof Choice) {
                    Log.d(TAG, "Choix adversaire reçu");
                    setOpponentChoice((Choice) response);

                    clientResponseListener.onReceive(getOwnChoice(), getOpponentChoice());
                    this.resetChoices();
                }

                Log.d(TAG, "Attente suite");
                Object nextResponse = this.incomingFlow.readObject();

                if (nextResponse instanceof RequestNextRound) {
                    Log.d(TAG, "NEXT");
                    clientRoundListener.onNext();
                }
                // TODO Endgame

            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Log.d(TAG, "Client déconnecté");
    }

    public ObjectOutputStream getOutgoingFlow() {
        return this.outgoingFlow;
    }
}
