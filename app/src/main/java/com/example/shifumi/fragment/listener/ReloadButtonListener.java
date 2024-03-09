package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.MainActivity;

/**
 * Ecouteur de rechargement de la liste d'appareils wifi disponibles
 */
public class ReloadButtonListener implements View.OnClickListener{
    private final MainActivity mainActivity;

    /**
     * Ecouteur de rechargement de la liste d'appareils wifi disponibles
     *
     * @param mainActivity activité principale
     */
    public ReloadButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.getPeerToPeerManager().discoverPeers(); // relance de l'écouteur d'appareils en WiFi direct
    }
}
