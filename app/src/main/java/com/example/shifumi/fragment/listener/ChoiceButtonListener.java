package com.example.shifumi.fragment.listener;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.ImageView;

import com.example.shifumi.fragment.PlayFragment;
import com.example.shifumi.game.Choice;

public class ChoiceButtonListener implements View.OnClickListener{
    private final PlayFragment fragment;
    private final int drawableId;
    private final ImageView ivSelectedChoice;
    private final Choice choice;

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
        // addRedBorder(ivSelectedChoice);
    }

    private void addRedBorder(ImageView imageView) {
        // TODO modifier le code
        // Création d'un ShapeDrawable pour le cadre rouge
        ShapeDrawable borderDrawable = new ShapeDrawable(new RectShape());
        borderDrawable.getPaint().setColor(0xFFFF0000);

        // Création d'un LayerDrawable pour combiner l'image existante et le cadre rouge
        Drawable[] layers = new Drawable[2];
        layers[0] = imageView.getDrawable(); // Image existante
        layers[1] = borderDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(layers);

        // Appliquer le LayerDrawable à l'ImageView
        imageView.setImageDrawable(layerDrawable);
    }
}
