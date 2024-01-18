package com.example.shifumi.network.listener;

import android.os.Bundle;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.EndgameFragment;
import com.example.shifumi.fragment.GameFragment;
import com.example.shifumi.fragment.PlayFragment;
import com.example.shifumi.game.Choice;

/**
 * Gestion d'une partie de Shifumi coté client
 */
public class ClientRoundListener {
    private final MainActivity mainActivity;

    /**
     *
     * @param mainActivity activité principale de l'application
     */
    public ClientRoundListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Réalisation de l'action "suivant" dans le jeu
     */
    public void onNext() {
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new PlayFragment())
                .commit();
    }

    /**
     * Réalisation de l'action "fin de partie" dans le jeu
     */
    public void onEnd() {
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new EndgameFragment())
                .commit();
    }

    /**
     * Mise à jour des choix coté client
     *
     * @param ownChoice choix du joueur actuel
     * @param opponentChoice choix de l'adversaire
     */
    public void onReceive(Choice ownChoice, Choice opponentChoice) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(GameFragment.OWN_CHOICE, ownChoice);
        bundle.putSerializable(GameFragment.OPPONENT_CHOICE, opponentChoice);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(bundle);

        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }
}
