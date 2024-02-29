package com.example.shifumi.network.handler;

import com.example.shifumi.network.Server;

import java.io.IOException;

/**
 * Traitement coté serveur d'une demande cliente
 */
public abstract class RequestHandler {
    protected final Server server;

    /**
     * Traitement coté serveur d'une demande cliente
     *
     * @param server serveur gérant la partie
     */
    public RequestHandler(Server server) {
        this.server = server;
    }

    /**
     * Initialisation du prochain maillon de la chaine de responsabilité
     * @param nextHandler prochain gestionnaire de demande cliente
     */
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    private RequestHandler nextHandler;

    /**
     * Traitement de la demande ou transfert au prochain maillon de la chaîne de responsabilité
     *
     * @param object demande cliente à traiter
     * @throws IOException erreur liée à l'envoi de données
     * @throws InterruptedException erreur liée à l'attente ou arrêt d'un thread
     */
    public void handle(Object object) throws InterruptedException, IOException {
        if(this.nextHandler!=null){
            this.nextHandler.handle(object);
        }
    }
}
