package com.example.shifumi.fragment.listener;

import android.view.View;
import android.widget.ImageView;

import com.example.shifumi.fragment.PlayFragment;
import com.example.shifumi.game.Choice;

/**
 * Ecouteur de sélection de choix
 */
public class ChoiceButtonListener implements View.OnClickListener{
    private final PlayFragment fragment;
    private final int drawableId;
    private final ImageView ivSelectedChoice;
    private final Choice choice;

    /**
     * Ecouteur de sélection de choix
     *
     * @param fragment fragment gérant la sélection des choix
     * @param ivSelectedChoice image de choix sélectionnée
     * @param drawableId identifiant de la ressource accossiée au choix
     * @param choice choix sélectionné
     */
    public ChoiceButtonListener(PlayFragment fragment, ImageView ivSelectedChoice, int drawableId, Choice choice) {
        this.fragment = fragment;
        this.ivSelectedChoice = ivSelectedChoice;
        this.drawableId = drawableId;
        this.choice = choice;
    }

    @Override
    public void onClick(View v) {
        ivSelectedChoice.setImageResource(drawableId);
        ivSelectedChoice.setVisibility(View.VISIBLE);
        fragment.setSelectedChoice(choice);
    }

}
