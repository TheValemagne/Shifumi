package com.example.shifumi.p2p.handler;

import android.content.Intent;

import com.example.shifumi.MainActivity;

/**
 * Gestionnaire d'un événement liée au réseau WiFi direct
 */
public abstract class P2pHandler {
    protected final MainActivity mainActivity;

    /**
     * Gestionnaire d'un événement liée au réseau WiFi direct
     *
     * @param mainActivity activité principale
     */
    protected P2pHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Initialisation du prochain maillon de la chaine de respnsabilité
     *
     * @param nextHandler prochain gestionnaire d'événement liée au réseau WiFi direct
     */
    public void setNextHandler(P2pHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    private P2pHandler nextHandler;

    /**
     * Traitement de l'intent ou transfert au prochain maillon de la chaîne de responsabilité
     *
     * @param intent opération à traiter
     */
    public void handle(Intent intent) {
        if(this.nextHandler!=null){
            this.nextHandler.handle(intent);
        }
    }
}
