package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.listener.NextButtonListener;
import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;
import com.example.shifumi.game.Result;

import java.text.MessageFormat;
import java.util.EnumMap;

public class GameFragment extends Fragment {

    private ImageView imageResult;

    private Choice ownChoice;
    private Choice opponentChoice;
    public static final String ARG_PARAM1 = "ownChoice";
    public static final String ARG_PARAM2 = "opponentChoice";

    public GameFragment() {
        // Constructeur vide requis par Android
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ownChoice = (Choice) getArguments().getSerializable(ARG_PARAM1);
            opponentChoice = (Choice) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        ImageView imagePlayer = view.findViewById(R.id.imagePlayer);
        ImageView imageOpponent = view.findViewById(R.id.imageOpponent);
        imageResult = view.findViewById(R.id.imageResult);

        MainActivity mainActivity = (MainActivity) requireActivity();
        Game game = mainActivity.getGame();

        // Récupération des choix du joueur et de l'adversaire depuis les arguments
        EnumMap<Choice, Integer> imageMap = new EnumMap<>(Choice.class);
        imageMap.put(Choice.PAPER, R.drawable.paper);
        imageMap.put(Choice.ROCK, R.drawable.rock);
        imageMap.put(Choice.SCISSORS, R.drawable.scissors);

        // Définir les images du joueur et de l'adversaire
        imagePlayer.setImageResource(imageMap.get(ownChoice));
        imageOpponent.setImageResource(imageMap.get(opponentChoice));
        imageOpponent.setRotation(180);

        Result result = game.hasWon(ownChoice, opponentChoice);
        game.updateScore(result);

        TextView gameScore = view.findViewById(R.id.gameScore);
        gameScore.setText(MessageFormat.format("Score : {0} : {1}", game.getPlayerScore(), game.getOpponentScore()));

        afficherResultat(result);

        Button nextBtn = view.findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new NextButtonListener(mainActivity.getSendObjectHandler()));

        return view;
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