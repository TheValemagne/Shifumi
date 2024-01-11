package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.ClientHandlerListener;

import java.io.IOException;
import java.net.Socket;

public final class ClientHandler extends ClientBase{
    private static final String TAG = "ClientHandler";
    private final ClientHandlerListener choiceUpdateListener;

    public ClientHandler(Socket socket, ClientHandlerListener choiceUpdateListener) throws IOException {
        super(socket);

        this.choiceUpdateListener = choiceUpdateListener;
    }

    @Override
    public void run() {
        super.run();

        while (!this.isInterrupted()){
            try {
                Object response = this.incomingFlow.readObject();

                if (response instanceof Choice) {
                    Log.d(TAG, "Choix reçu : " + response);

                    choiceUpdateListener.onReceive((Choice) response);

                    synchronized (opponentChoiceLock) {
                        while (getOpponentChoice().equals(Choice.UNSET)) {
                            opponentChoiceLock.wait();
                        }

                        this.outgoingFlow.writeObject(getOpponentChoice());
                    }
                } // TODO Endrequest or Next

            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
