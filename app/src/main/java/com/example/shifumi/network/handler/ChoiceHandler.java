package com.example.shifumi.network.handler;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.ClientHandler;
import com.example.shifumi.network.Server;

import java.io.IOException;

/**
 * Traitement coté serveur d'un choix envoyé par un client joueur
 */
public final class ChoiceHandler extends RequestHandler{

    private final ClientHandler clientHandler;
    private final int clientId;

    /**
     * Traitement coté serveur d'un choix envoyé par un client joueur
     *
     * @param server serveur gérant la partie
     * @param clientHandler interlocuteur du client à l'origine du message
     * @param clientId identifiant du client à l'origine du message
     */
    public ChoiceHandler(Server server, ClientHandler clientHandler, int clientId) {
        super(server);

        this.clientHandler = clientHandler;
        this.clientId = clientId;
    }

    @Override
    public void handle(Object object) throws InterruptedException, IOException {
        if(!(object instanceof Choice)) {
            super.handle(object);
            return;
        }

        Choice choice = (Choice) object;

        server.setChoice(clientId, choice); // mise à jour des données du serveur

        if(server.areAllChoicesSet()) { // lorsque que tous les joueurs ont validé leur choix
            // Mise à jour des choix des adversaires pour les deux interlocuteurs clients
            server.getClientHandler(0).setOpponentChoice(server.getChoice(1));
            server.getClientHandler(1).setOpponentChoice(server.getChoice(0));
            server.resetChoices(); // réinitialisation des choix des joueurs
        }

        synchronized (clientHandler.getOpponentChoiceLock()) { // envoie du choix de l'adversaire après actualisation par le serveur
            while (clientHandler.getOpponentChoice().equals(Choice.UNSET)) {
                clientHandler.getOpponentChoiceLock().wait();
            }

            clientHandler.getOutgoingFlow().writeObject(clientHandler.getOpponentChoice()); // envoit du choix de l'adversaire au joueur correspondant
        }
        clientHandler.resetChoices(); // réinitialisation des choix des deux joueurs
    }
}
