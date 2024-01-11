package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.listener.ChoiceUpdateListener;
import com.example.shifumi.network.listener.GameManagementListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Server extends Thread {
    private static final String TAG = "Server";
    private ServerSocket serverSocket;
    public static final int port = 8888;
    private final List<ClientHandler> clients;
    private final List<Choice> choices;
    private final List<Object> choiceLocks;

    public Server() {
        this.clients = new ArrayList<>();
        this.choices = new ArrayList<>(Arrays.asList(Choice.UNSET, Choice.UNSET));
        this.choiceLocks = new ArrayList<>(Arrays.asList(new Object(), new Object()));
    }

    @Override
    public void run() {
        super.run();

        try {
            Log.d(TAG, "Lancement du serveur");
            serverSocket = new ServerSocket(port);
            int clientId = 0;

            while (!this.isInterrupted()){
                Socket socket = serverSocket.accept();

                if (clients.size() > 2) { // accepte que deux joueurs par partie
                    socket.close();
                    continue;
                }

                Log.d(TAG, "Nouveau client " + clientId);
                ClientHandler clientHandler = new ClientHandler(socket,
                        new ChoiceUpdateListener(clientId, this),
                        new GameManagementListener(this));

                clientHandler.start();
                clients.add(clientHandler);
                clientId++;
            }

            Log.d(TAG, "Fermeture du serveur");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Choice getChoice(int index) {
        synchronized (this) {
            return choices.get(index);
        }
    }

    public void setChoice(int index, Choice choice) {
        synchronized (this) {
            synchronized (choiceLocks.get(index)) {
                choices.set(index, choice);
                choiceLocks.get(index).notify();
            }
        }
    }

    public boolean areAllChoicesSet() {
        synchronized (this) {
            return choices.stream().noneMatch(choice -> choice.equals(Choice.UNSET));
        }
    }

    public void resetChoices() {
        synchronized (this) {
            Collections.fill(choices, Choice.UNSET);
        }
    }

    public void sendToAll(Object object) {
        this.clients.forEach(clientHandler -> {
            try {
                clientHandler.sendObject(object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ClientHandler getClient(int index) {
        return clients.get(index);
    }

    private void closeClient(ClientHandler clientHandler) {
        try {
            clientHandler.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            clients.forEach(this::closeClient);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
