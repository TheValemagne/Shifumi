package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.listener.ChoiceButtonListener;
import com.example.shifumi.fragment.listener.ValidateButtonListener;
import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayFragment extends Fragment {

    public void setSelectedChoice(Choice selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    public Choice getSelectedChoice() {
        return selectedChoice;
    }

    private Choice selectedChoice;

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        ImageView ivSelectedChoice = view.findViewById(R.id.ivSelectedChoice);
        GridLayout gridLayout = view.findViewById(R.id.gridLayout);

        // Ajouter un écouteur de clic à chaque bouton
        Button btnRock = view.findViewById(R.id.btnRock);
        btnRock.setOnClickListener(new ChoiceButtonListener(this, ivSelectedChoice, R.drawable.rock, Choice.ROCK));

        Button btnPaper = view.findViewById(R.id.btnPaper);
        btnPaper.setOnClickListener(new ChoiceButtonListener(this, ivSelectedChoice, R.drawable.paper, Choice.PAPER));

        Button btnScissors = view.findViewById(R.id.btnScissors);
        btnScissors.setOnClickListener(new ChoiceButtonListener(this, ivSelectedChoice, R.drawable.scissors, Choice.SCISSORS));

        Button btnValidate = view.findViewById(R.id.playButton);

        List<Button> buttons = new ArrayList<>(Arrays.asList(
                btnRock,
                btnPaper,
                btnScissors,
                btnValidate
        ));

        MainActivity mainActivity = (MainActivity) requireActivity();
        btnValidate.setOnClickListener(new ValidateButtonListener(this, mainActivity, buttons));

        TextView playScore = view.findViewById(R.id.playScore);
        Game game = mainActivity.getGame();
        playScore.setText(MessageFormat.format("Score : {0} : {1}", game.getPlayerScore(), game.getOpponentScore()));

        return view;
    }
}