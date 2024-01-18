package com.example.shifumi.network.listener;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.request.RequestEndgame;
import com.example.shifumi.network.request.RequestNextRound;
import com.example.shifumi.network.Server;

/**
 * Gestion d'une partie de Shifumi coté serveur
 */
public class GameManagementListener {
    private final int clientId;
    private final Server server;

    /**
     *
     * @param clientId numéro du client associé
     * @param server instance du serveur
     */
    public GameManagementListener(int clientId, Server server) {
        this.clientId = clientId;
        this.server = server;
    }

    /**
     * Gestion de la requête "suivant"
     */
    public void onNext() {
        server.sendToAll(new RequestNextRound());
    }

    /**
     * Gestion de la requête "fin de partie"
     */
    public void onEnd() {
        server.sendToAll(new RequestEndgame());
    }

    /**
     * Gestion de la réception d'un choix de joueur
     */
    public void onReceive(Choice choice) {
        server.setChoice(clientId, choice); // mise à jour des données du serveur

        if(server.areAllChoicesSet()) { // lorsque que tous les jouers ont réaliser leur choix
            // Mise à jour des choix des adversaires pour les deux joueurs
            server.getClient(0).setOpponentChoice(server.getChoice(1));
            server.getClient(1).setOpponentChoice(server.getChoice(0));
            server.resetChoices();
        }
    }
}
