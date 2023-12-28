package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public final class Client extends Thread {
    private static final String TAG = "Client";
    private final InetAddress groupOwnerAddress;
    private final ObjectOutputStream outgoingFlow;
    private final ObjectInputStream incomingFlow;
    private final Game game;
    private final Socket socket;
    private Choice ownChoice;
    private final Object ownChoiceLock = new Object();
    private Choice opponentChoice;
    private final Object opponentChoiceLock = new Object();

    public Client(InetAddress groupOwnerAddress, Game game) throws IOException {
        this.groupOwnerAddress = groupOwnerAddress;
        this.game = game;

        this.socket = new Socket(groupOwnerAddress.getHostAddress(), Server.port);
        this.outgoingFlow = new ObjectOutputStream(socket.getOutputStream());
        this.incomingFlow = new ObjectInputStream(socket.getInputStream());

        resetChoice();
    }

    @Override
    public void run() {
        super.run();

        Log.d(TAG, String.format("Client connecté à l'adresse : %s, adresse client : %s", groupOwnerAddress, socket.getLocalAddress()));

        while (!socket.isClosed()) {
            try {
                // TODO game management
                synchronized (ownChoiceLock) {
                    while (ownChoice.equals(Choice.UNSET)) {
                        ownChoiceLock.wait();
                    }
                    this.outgoingFlow.writeObject(ownChoice);
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

    public Choice getOwnChoice() {
        return ownChoice;
    }

    private void setOwnChoice(Choice choice) {
        synchronized (ownChoiceLock) {
            ownChoice = choice;
            ownChoiceLock.notify();
        }
    }

    public Choice getOpponentChoice() {
        return opponentChoice;
    }

    private void setOpponentChoice(Choice choice) {
        synchronized (opponentChoiceLock) {
            opponentChoice = choice;
            opponentChoiceLock.notify();
        }
    }

    private void resetChoice() {
        setOwnChoice(Choice.UNSET);
        setOpponentChoice(Choice.UNSET);
    }
}
