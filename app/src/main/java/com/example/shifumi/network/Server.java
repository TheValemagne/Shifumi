package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Serveur gérant une partie multijoueur
 */
public final class Server extends Thread {
    private static final String TAG = "Server";
    private ServerSocket serverSocket;
    public static final int PORT = 8888;
    private final List<ClientHandler> clients;
    private final List<Choice> choices;
    private final List<Object> choiceLocks;

    /**
     * Serveur gérant une partie multijoueur
     */
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
            serverSocket = new ServerSocket(PORT); // lancement du serveur
            int clientId = 0;

            while (!this.isInterrupted()){
                Socket socket = serverSocket.accept();

                if (clients.size() > 2) { // accepte que deux joueurs par partie
                    socket.close();
                    continue;
                }

                Log.d(TAG, "Nouveau client " + clientId);
                ClientHandler clientHandler = new ClientHandler(clientId, socket, this); // création d'un interlocuteur

                clientHandler.start();
                clients.add(clientHandler);
                clientId++;
            }

            Log.d(TAG, "Fermeture du serveur");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne le choix d'un joueur
     *
     * @param index index du joueur
     * @return choix du joueur
     */
    public Choice getChoice(int index) {
        synchronized (this) {
            return choices.get(index);
        }
    }

    /**
     * Modifier le choix d'un joueur
     *
     * @param index index du joueur
     * @param choice nouveau choix
     */
    public void setChoice(int index, Choice choice) {
        synchronized (this) {
            synchronized (choiceLocks.get(index)) {
                choices.set(index, choice);
                choiceLocks.get(index).notify();
            }
        }
    }

    /**
     * Vérifie si tous les jouers ont définis leur choix
     *
     * @return vrai si tous les joueurs ont valider leur choix, sinon faux
     */
    public boolean areAllChoicesSet() {
        synchronized (this) {
            return choices.stream().noneMatch(choice -> choice.equals(Choice.UNSET));
        }
    }

    /**
     * Remise à zéro des choix
     */
    public void resetChoices() {
        synchronized (this) {
            Collections.fill(choices, Choice.UNSET);
        }
    }

    /**
     * Envoi un objet à tous les clients connectés
     *
     * @param object données à envoyer
     */
    public void sendToAll(Object object) {
        this.clients.forEach(clientHandler -> {
            try {
                clientHandler.sendObject(object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Retourne un interlocuteur client
     *
     * @param index index du client voulue
     * @return intercepteur d'un client
     */
    public ClientHandler getClientHandler(int index) {
        return clients.get(index);
    }

    /**
     * Fermeture du client
     *
     * @param clientHandler client à fermer
     */
    private void closeClient(ClientHandler clientHandler) {
        try {
            clientHandler.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fermeture des connexions du serveur
     */
    public void closeConnection() {
        try {
            clients.forEach(this::closeClient);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
