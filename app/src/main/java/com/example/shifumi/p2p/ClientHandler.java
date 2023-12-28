package com.example.shifumi.p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final int clientId;
    private final Socket socket;
    private final Server server;
    private final ObjectOutputStream outgoingFlow;
    private final ObjectInputStream incomingFlow;


    public ClientHandler(int clientId, Socket socket, Server server) throws IOException {
        this.clientId = clientId;
        this.socket = socket;
        this.server = server;
        this.outgoingFlow = new ObjectOutputStream(socket.getOutputStream());
        this.incomingFlow = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        super.run();

        while (!this.isInterrupted()){

        }
    }

    public void close() throws IOException {
        this.incomingFlow.close();
        this.outgoingFlow.close();
        this.socket.close();
        this.interrupt();
    }
}
