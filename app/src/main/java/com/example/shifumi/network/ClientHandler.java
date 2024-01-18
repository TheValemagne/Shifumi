package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.GameManagementListener;
import com.example.shifumi.network.request.RequestEndgame;
import com.example.shifumi.network.request.RequestNextRound;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public final class ClientHandler extends ClientBase {
    private static final String TAG = "ClientHandler";
    private final GameManagementListener gameManagementListener;

    public ClientHandler(Socket socket,
                         GameManagementListener gameManagementListener) throws IOException {
        super(socket);

        this.gameManagementListener = gameManagementListener;
    }

    @Override
    public void run() {
        super.run();

        while (!this.isInterrupted()) {
            try {
                Object response = this.incomingFlow.readObject();
                Log.d(TAG, "reçu : " + response);

                if (response instanceof Choice) {
                    Log.d(TAG, "Choix reçu : " + response);

                    gameManagementListener.onReceive((Choice) response);

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
                    gameManagementListener.onNext();
                } else if (response instanceof RequestEndgame) {
                    gameManagementListener.onEnd();
                }

            } catch (SocketException | InterruptedException | EOFException e) {
                Log.e(TAG, e.toString());
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
