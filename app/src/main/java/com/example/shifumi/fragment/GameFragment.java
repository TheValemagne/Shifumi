package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import com.example.shifumi.R;
import com.example.shifumi.game.Result;

public class GameFragment extends Fragment {

    private ImageView imagePlayer;
    private ImageView imageOpponent;
    private ImageView imageResult;

    public GameFragment() {
        // Constructeur vide requis par Android
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        imagePlayer = view.findViewById(R.id.imagePlayer);
        imageOpponent = view.findViewById(R.id.imageOpponent);
        imageResult = view.findViewById(R.id.imageResult);

        // Récupération des choix du joueur et de l'adversaire depuis les arguments
        Bundle args = getArguments();
        if (args != null) {
            int choixJoueur = args.getInt("choixJoueur", R.drawable.ic_launcher_foreground);
            int choixAdversaire = args.getInt("choixAdversaire", R.drawable.ic_launcher_foreground);

            // Définir les images du joueur et de l'adversaire
            imagePlayer.setImageResource(choixJoueur);
            imageOpponent.setImageResource(choixAdversaire);

            Result resultat = determinerResultat(choixJoueur, choixAdversaire);

            afficherResultat(resultat);
        }

        return view;
    }

    // Méthode pour déterminer le résultat du jeu
    private Result determinerResultat(int choixJoueur, int choixAdversaire) {

        if (choixJoueur == choixAdversaire) {
            return Result.DRAW;
        } else {
            // Logique pour déterminer si le joueur a gagné ou perdu
            return Result.WIN;
        }
    }

    // Méthode pour afficher la croix rouge, la coche ou la croix orange en fonction du résultat
    private void afficherResultat(Result resultat) {
        switch (resultat) {
            case WIN:
                imageResult.setImageResource(R.drawable.coche);
                break;
            case LOST:
                imageResult.setImageResource(R.drawable.croix_rouge);
                break;
            case DRAW:
                imageResult.setImageResource(R.drawable.croix_orange);
                break;
        }
        // Rendre l'image visible
        imageResult.setVisibility(View.VISIBLE);
    }
}