package com.example.shifumi.fragment.placeholder;

import android.widget.Button;

import com.example.shifumi.game.Choice;

/**
 * Conteneur d'information d'un choix de jeu
 */
public class ChoiceButtonContent {
    private final Button button;

    public Button getButton() {
        return button;
    }

    private final int drawableId;

    public int getDrawableId() {
        return drawableId;
    }

    private final Choice choice;

    public Choice getChoice() {
        return choice;
    }

    /**
     * Conteneur d'information d'un choix de jeu
     *
     * @param button bouton de sélection du choix
     * @param drawableId identifiant de la ressource graphique du choix
     * @param choice choix représenté
     */
    public ChoiceButtonContent(Button button, int drawableId, Choice choice) {
        this.button = button;
        this.drawableId = drawableId;
        this.choice = choice;
    }
}
