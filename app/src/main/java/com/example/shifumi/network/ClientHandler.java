package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.network.handler.ChoiceHandler;
import com.example.shifumi.network.handler.RequestEndgameHandler;
import com.example.shifumi.network.handler.RequestHandler;
import com.example.shifumi.network.handler.RequestNextRoundHandler;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ClientHandler extends ClientBase {
    private static final String TAG = "ClientHandler";
    private final List<RequestHandler> handlers;

    public ClientHandler(int clientId,
                         Socket socket,
                         Server server) throws IOException {
        super(socket);

        handlers = new ArrayList<>(Arrays.asList(
                new ChoiceHandler(server, this, clientId),
                new RequestNextRoundHandler(server),
                new RequestEndgameHandler(server)
        ));

        for (int index = 0; index < handlers.size() - 1; index++) {
            handlers.get(index).setNextHandler(handlers.get(index + 1));
        }
    }

    /**
     * Envoi des données au client
     *
     * @param object données à envoyer
     * @throws IOException erreur lors de l'envoi des données
     */
    public void sendObject(Object object) throws IOException {
        Log.d(TAG, "sending : " + object);
        this.outgoingFlow.writeObject(object);
    }

    @Override
    public void run() {
        super.run();

        while (!this.isInterrupted()) {
            try {
                Object response = this.incomingFlow.readObject();

                this.handlers.get(0).handle(response);
            } catch (SocketException | InterruptedException | EOFException e) {
                Log.e(TAG, e.toString());
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
