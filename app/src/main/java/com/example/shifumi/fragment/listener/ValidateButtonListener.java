package com.example.shifumi.fragment.listener;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.PlayFragment;
import com.example.shifumi.game.Choice;

import java.util.List;

/**
 * Ecouteur de validation du choix
 */
public class ValidateButtonListener implements View.OnClickListener {
    private final PlayFragment fragment;
    private final MainActivity mainActivity;
    private final List<Button> buttons;

    /**
     * Ecouteur de validation du choix
     *
     * @param fragment fragment de gestion du jeu
     * @param mainActivity activité principale
     * @param buttons liste de boutons de sélection de choix
     */
    public ValidateButtonListener(PlayFragment fragment, MainActivity mainActivity, List<Button> buttons) {
        this.fragment = fragment;
        this.mainActivity = mainActivity;
        this.buttons = buttons;
    }

    @Override
    public void onClick(View v) {
        if (fragment.getSelectedChoice().equals(Choice.UNSET)) { // choix non définie
            Toast.makeText(mainActivity, R.string.please_make_a_choice_before_validation, Toast.LENGTH_LONG).show();
            return;
        }

        mainActivity.getClient().setOwnChoice(fragment.getSelectedChoice()); // mise à jour du choix du joueur
        buttons.forEach(button -> button.setEnabled(false)); // déactivation des boutons de sélection de choix
    }
}
