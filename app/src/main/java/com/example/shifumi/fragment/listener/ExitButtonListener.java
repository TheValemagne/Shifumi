package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.MainActivity;

/**
 * Ecouteur pour quitter l'application
 */
public class ExitButtonListener implements View.OnClickListener{
    private final MainActivity mainActivity;

    /**
     * Ecouteur pour quitter l'application
     *
     * @param mainActivity activité principale
     */
    public ExitButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.finish();
        System.exit(0); // fermeture de l'application
    }
}

