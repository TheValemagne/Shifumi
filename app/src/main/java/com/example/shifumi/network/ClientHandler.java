package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.ClientHandlerListener;
import com.example.shifumi.network.listener.GameManagementListener;

import java.io.IOException;
import java.net.Socket;

public final class ClientHandler extends ClientBase{
    private static final String TAG = "ClientHandler";
    private final ClientHandlerListener choiceUpdateListener;
    private final GameManagementListener gameManagementListener;

    public ClientHandler(Socket socket,
                         ClientHandlerListener choiceUpdateListener,
                         GameManagementListener gameManagementListener) throws IOException {
        super(socket);

        this.choiceUpdateListener = choiceUpdateListener;
        this.gameManagementListener = gameManagementListener;
    }

    @Override
    public void run() {
        super.run();

        while (!this.isInterrupted()){
            try {
                Log.d(TAG, "Attente readObject");
                Object response = this.incomingFlow.readObject();
                Log.d(TAG, "reçu : " + response);

                if (response instanceof Choice) {
                    Log.d(TAG, "Choix reçu : " + response);

                    choiceUpdateListener.onReceive((Choice) response);

                    synchronized (opponentChoiceLock) {
                        while (getOpponentChoice().equals(Choice.UNSET)) {
                            Log.d(TAG, "waiting opponent choice : request - " + response);
                            opponentChoiceLock.wait();
                        }
                        Log.d(TAG, "opponent ready to send - " + response);

                        this.outgoingFlow.writeObject(getOpponentChoice());
                    }
                    this.resetChoices();
                } else if (response instanceof RequestNextRound) {
                    Log.d(TAG, "NEXT round");
                    gameManagementListener.onNext();
                }
                // TODO Endrequest

            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
