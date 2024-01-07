package com.example.shifumi.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.shifumi.R;

public class PlayFragment extends Fragment {

    private ImageView ivSelectedChoice;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        ivSelectedChoice = view.findViewById(R.id.ivSelectedChoice);
        GridLayout gridLayout = view.findViewById(R.id.gridLayout);

        // Ajouter un écouteur de clic à chaque bouton
        Button btnRock = view.findViewById(R.id.btnRock);
        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mettre à jour l'image de l'ImageView
                ivSelectedChoice.setImageResource(R.drawable.rock);
                ivSelectedChoice.setVisibility(View.VISIBLE);
            }
        });

        Button btnPaper = view.findViewById(R.id.btnPaper);
        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelectedChoice.setImageResource(R.drawable.paper);
                ivSelectedChoice.setVisibility(View.VISIBLE);
            }
        });

        Button btnScissors = view.findViewById(R.id.btnScissors);
        btnScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelectedChoice.setImageResource(R.drawable.scissors);
                ivSelectedChoice.setVisibility(View.VISIBLE);
                addRedBorder(ivSelectedChoice);
            }
        });

        Button boutonJouer = view.findViewById(R.id.playButton);
        boutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer et afficher le fragment GameFragment
                GameFragment gameFragment = new GameFragment();
                //.replace(R.id.main_frame, gameFragment)
            }
        });
        return view;
    }

    public static void addRedBorder(ImageView imageView) {
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