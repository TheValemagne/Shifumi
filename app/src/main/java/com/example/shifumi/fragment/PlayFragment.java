package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.listener.ChoiceButtonListener;
import com.example.shifumi.game.Choice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayFragment extends Fragment {

    private ImageView ivSelectedChoice;

    public void setSelectedChoice(Choice selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    private Choice selectedChoice;
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

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) requireActivity();
                System.out.println("Selected choice player : " + selectedChoice);

                mainActivity.getClient().setOwnChoice(selectedChoice);
                buttons.forEach(button -> button.setEnabled(false));
            }
        });
        return view;
    }
}