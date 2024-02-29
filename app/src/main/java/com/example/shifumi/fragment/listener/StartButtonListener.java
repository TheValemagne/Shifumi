package com.example.shifumi.fragment.listener;

import android.view.View;
import android.widget.Button;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.WifiDevicesFragment;

/**
 * Ecouteur de lancement de la recherche d'appareil
 */
public class StartButtonListener implements Button.OnClickListener {
    private final MainActivity mainActivity;

    /**
     * Ecouteur de lancement de la recherche d'appareil
     *
     * @param mainActivity activité principale
     */
    public StartButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.getPeerToPeerManager().discoverPeers(); // lancement de la découverte des pairs

        // affichage du fragment avec la lsite de sélection des appareils disponibles
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new WifiDevicesFragment())
                .commit();
    }
}
