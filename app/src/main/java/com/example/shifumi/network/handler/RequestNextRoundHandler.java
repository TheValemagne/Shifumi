package com.example.shifumi.network.handler;

import com.example.shifumi.network.Server;
import com.example.shifumi.network.request.RequestNextRound;

import java.io.IOException;

/**
 * Traitement coté serveur de la demande pour la prochaine manche
 */
public class RequestNextRoundHandler extends RequestHandler{
    /**
     * Traitement coté serveur d'ne requête manche suivante
     *
     * @param server serveur gérant la partie
     */
    public RequestNextRoundHandler(Server server) {
        super(server);
    }

    @Override
    public void handle(Object object) throws IOException, InterruptedException {
        if (!(object instanceof RequestNextRound)) {
            super.handle(object);
            return;
        }

        server.sendToAll(new RequestNextRound()); // envoi de la requête pour passer à la manche suivante
    }
}
