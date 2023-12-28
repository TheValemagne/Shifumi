package com.example.shifumi.p2p;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private static final String TAG = "ClientHandler";
    private final int clientId;
    private final Socket socket;
    private final ObjectOutputStream outgoingFlow;
    private final ObjectInputStream incomingFlow;
    private final Server server;
    private final Game game;

    private Choice ownChoice;
    private final Object ownChoiceLock = new Object();
    private Choice opponentChoice;
    private final Object opponentChoiceLock = new Object();

    public ClientHandler(int clientId, Socket socket, Server server, Game game) throws IOException {
        this.clientId = clientId;
        this.server = server;
        this.game = game;

        this.socket = socket;
        this.outgoingFlow = new ObjectOutputStream(socket.getOutputStream());
        this.incomingFlow = new ObjectInputStream(socket.getInputStream());
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
                    // TODO do something with server if both choices are set

                    synchronized (opponentChoiceLock) {
                        while (opponentChoice.equals(Choice.UNSET)) {
                            opponentChoiceLock.wait();
                        }

                        this.outgoingFlow.writeObject(opponentChoice);
                    }
                }

            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Choice getOwnChoice() {
        return ownChoice;
    }

    public void setOwnChoice(Choice choice) {
        synchronized (ownChoiceLock) {
            ownChoice = choice;
            ownChoiceLock.notify();
        }
    }

    public Choice getOpponentChoice() {
        return opponentChoice;
    }

    public void setOpponentChoice(Choice choice) {
        synchronized (opponentChoiceLock) {
            opponentChoice = choice;
            opponentChoiceLock.notify();
        }
    }

    public void close() throws IOException {
        this.incomingFlow.close();
        this.outgoingFlow.close();
        this.socket.close();
        this.interrupt();
    }
}
