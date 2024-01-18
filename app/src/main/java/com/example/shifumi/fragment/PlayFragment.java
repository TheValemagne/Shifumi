package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.databinding.FragmentPlayBinding;
import com.example.shifumi.fragment.listener.ChoiceButtonListener;
import com.example.shifumi.fragment.listener.ValidateButtonListener;
import com.example.shifumi.fragment.placeholder.ChoiceButtonContent;
import com.example.shifumi.game.Choice;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPlayBinding binding = FragmentPlayBinding.inflate(inflater, container, false);

        List<ChoiceButtonContent> choiceButtonContents = new ArrayList<>(Arrays.asList(
                new ChoiceButtonContent(binding.btnRock, R.drawable.rock, Choice.ROCK),
                new ChoiceButtonContent(binding.btnPaper, R.drawable.paper, Choice.PAPER),
                new ChoiceButtonContent(binding.btnScissors, R.drawable.scissors, Choice.SCISSORS)
        ));

        // Ajouter un écouteur de clic à chaque bouton choix
        for (ChoiceButtonContent content : choiceButtonContents) {
            content.getButton().setOnClickListener(new ChoiceButtonListener(this, binding.ivSelectedChoice, content.getDrawableId(), content.getChoice()));
        }

        List<Button> buttons = new ArrayList<>(Arrays.asList(
                binding.btnRock,
                binding.btnPaper,
                binding.btnScissors,
                binding.playButton
        ));

        MainActivity mainActivity = (MainActivity) requireActivity();
        binding.playButton.setOnClickListener(new ValidateButtonListener(this, mainActivity, buttons));

        binding.playScore.setText(mainActivity.getScoreMsg());

        return binding.getRoot();
    }
}