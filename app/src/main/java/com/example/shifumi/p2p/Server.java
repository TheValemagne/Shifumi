package com.example.shifumi.p2p;

import com.example.shifumi.game.Choice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private final int port = 8888;
    private final List<ClientHandler> clients;
    private final List<Choice> choices;

    public Server() {
        this.clients = new ArrayList<>();
        this.choices = new ArrayList<>(Arrays.asList(Choice.UNSET, Choice.UNSET));
    }

    @Override
    public void run() {
        super.run();

        try {
            serverSocket = new ServerSocket(port);
            int clientId = 0;

            while (!this.isInterrupted()){
                Socket socket = serverSocket.accept();

                if (clients.size() >= 2) {
                    socket.close();
                    continue;
                }

                ClientHandler clientHandler = new ClientHandler(clientId, socket, this);
                clients.add(clientHandler);
                clientId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Choice getChoice(int clientId) {
        synchronized (this) {
            return choices.get(clientId);
        }
    }

    public void resetChoices() {
        synchronized (this) {
            Collections.fill(choices, Choice.UNSET);
        }
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
