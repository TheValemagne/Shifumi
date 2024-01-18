package com.example.shifumi.network.handler;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.ClientHandler;
import com.example.shifumi.network.Server;

import java.io.IOException;

public final class ChoiceHandler extends RequestHandler{

    private final ClientHandler clientHandler;
    private final int clientId;

    public ChoiceHandler(Server server, ClientHandler clientHandler, int clientId) {
        super(server);

        this.clientHandler = clientHandler;
        this.clientId = clientId;
    }

    @Override
    public void handle(Object object) throws IOException, InterruptedException {
        if(!(object instanceof Choice)) {
            super.handle(object);
            return;
        }

        Choice choice = (Choice) object;

        server.setChoice(clientId, choice); // mise à jour des données du serveur

        if(server.areAllChoicesSet()) { // lorsque que tous les joueurs ont réalisé leur choix
            // Mise à jour des choix des adversaires pour les deux joueurs
            server.getClient(0).setOpponentChoice(server.getChoice(1));
            server.getClient(1).setOpponentChoice(server.getChoice(0));
            server.resetChoices();
        }

        synchronized (clientHandler.getOpponentChoiceLock()) {
            while (clientHandler.getOpponentChoice().equals(Choice.UNSET)) {
                clientHandler.getOpponentChoiceLock().wait();
            }

            clientHandler.getOutgoingFlow().writeObject(clientHandler.getOpponentChoice());
        }
        clientHandler.resetChoices();
    }
}
