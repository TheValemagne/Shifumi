package com.example.shifumi.network.manager;

import android.os.Bundle;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.EndgameFragment;
import com.example.shifumi.fragment.GameRoundResultFragment;
import com.example.shifumi.fragment.PlayFragment;
import com.example.shifumi.game.Choice;

/**
 * Gestionnaire d'une partie de Shifumi coté client
 */
public class ClientRoundManager {
    private final MainActivity mainActivity;

    /**
     * Gestionnaire d'une partie de Shifumi coté client
     *
     * @param mainActivity activité principale de l'application
     */
    public ClientRoundManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Réalisation de l'action "suivant" dans le jeu
     */
    public void onNext() {
        // affichage de l'écran de sélection des choix
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new PlayFragment())
                .commit();
    }

    /**
     * Réalisation de l'action "fin de partie" dans le jeu
     */
    public void onEnd() {
        // affichage de l'écran de fin de partie
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new EndgameFragment())
                .commit();
    }

    /**
     * Mise à jour des choix coté client
     *
     * @param ownChoice choix du joueur
     * @param opponentChoice choix de l'adversaire
     */
    public void onReceive(Choice ownChoice, Choice opponentChoice) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(GameRoundResultFragment.OWN_CHOICE, ownChoice);
        bundle.putSerializable(GameRoundResultFragment.OPPONENT_CHOICE, opponentChoice);

        GameRoundResultFragment fragment = new GameRoundResultFragment();
        fragment.setArguments(bundle);

        // affichage du résultat de la manche
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }
}
