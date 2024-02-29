package com.example.shifumi.network.handler;

import com.example.shifumi.network.Server;
import com.example.shifumi.network.request.RequestEndgame;

import java.io.IOException;

/**
 * Traitement coté serveur d'une demande de fin de partie
 */
public class RequestEndgameHandler extends RequestHandler{
    /**
     * Traitement coté serveur d'une demande de fin de partie
     *
     * @param server serveur gérant la partie
     */
    public RequestEndgameHandler(Server server) {
        super(server);
    }

    @Override
    public void handle(Object object) throws IOException, InterruptedException {
        if (!(object instanceof RequestEndgame)) {
            super.handle(object);
            return;
        }

        server.sendToAll(new RequestEndgame()); // envoie de la re^quete de fin aux joueurs
    }
}
