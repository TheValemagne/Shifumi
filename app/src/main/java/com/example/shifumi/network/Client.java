package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.ClientRoundListener;
import com.example.shifumi.network.request.RequestEndgame;
import com.example.shifumi.network.request.RequestNextRound;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Classe pour la communication client joueur
 */
public final class Client extends ClientBase {
    private static final String TAG = "Client";
    private final InetAddress groupOwnerAddress;
    private final ClientRoundListener clientRoundListener;

    public Client(InetAddress groupOwnerAddress,
                  ClientRoundListener clientRoundListener) throws IOException {
        super(new Socket(groupOwnerAddress.getHostAddress(), Server.PORT));

        this.groupOwnerAddress = groupOwnerAddress;
        this.clientRoundListener = clientRoundListener;
    }

    @Override
    public void run() {
        super.run();

        Log.d(TAG, String.format("Client connecté à l'adresse : %s, adresse client : %s", groupOwnerAddress, socket.getLocalAddress()));

        while (!this.isInterrupted()) {
            try {
                playRound();

                Log.d(TAG, "Attente suite");
                Object nextResponse = this.incomingFlow.readObject();

                if (nextResponse instanceof RequestNextRound) {
                    Log.d(TAG, "NEXT");
                    clientRoundListener.onNext();
                } else if (nextResponse instanceof RequestEndgame) {
                    Log.d(TAG, "Endgame");
                    clientRoundListener.onEnd();
                }

            } catch (SocketException | InterruptedException e) {
                Log.e(TAG, e.toString());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Log.d(TAG, "Client déconnecté");
    }

    private void playRound() throws InterruptedException, IOException, ClassNotFoundException {
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

            clientRoundListener.onReceive(getOwnChoice(), getOpponentChoice());
            this.resetChoices();
        }
    }

    public ObjectOutputStream getOutgoingFlow() {
        return this.outgoingFlow;
    }
}
