package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.databinding.FragmentGameBinding;
import com.example.shifumi.fragment.listener.EndgameListener;
import com.example.shifumi.fragment.listener.NextButtonListener;
import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;
import com.example.shifumi.game.Result;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentGameBinding binding = FragmentGameBinding.inflate(inflater, container, false);

        EnumMap<Choice, Integer> imageMap = new EnumMap<>(Choice.class);
        imageMap.put(Choice.PAPER, R.drawable.paper);
        imageMap.put(Choice.ROCK, R.drawable.rock);
        imageMap.put(Choice.SCISSORS, R.drawable.scissors);

        MainActivity mainActivity = (MainActivity) requireActivity();

        // Définir les images du joueur et de l'adversaire
        binding.imagePlayer.setImageResource(imageMap.get(ownChoice));

        binding.imageOpponent.setImageResource(imageMap.get(opponentChoice));
        binding.imageOpponent.setRotation(180);

        Game game = mainActivity.getGame();
        Result result = game.hasWon(ownChoice, opponentChoice);
        game.updateScore(result);

        binding.gameScore.setText(mainActivity.getScoreMsg());

        imageResult = binding.imageResult;
        showResultImage(result);

        binding.nextBtn.setOnClickListener(new NextButtonListener(mainActivity.getSendObjectHandler()));
        binding.leaveBtn.setOnClickListener(new EndgameListener(mainActivity.getSendObjectHandler()));

        return binding.getRoot();
    }

    /**
     * Méthode pour afficher la croix rouge, la coche ou la croix orange en fonction du résultat
     *
     * @param result
     */
    private void showResultImage(Result result) {
        switch (result) {
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