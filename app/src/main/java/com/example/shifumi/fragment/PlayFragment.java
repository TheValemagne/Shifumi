package com.example.shifumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.shifumi.R;
import com.example.shifumi.fragment.listener.ChoiceButtonListener;

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
        btnRock.setOnClickListener(new ChoiceButtonListener(ivSelectedChoice, R.drawable.rock));

        Button btnPaper = view.findViewById(R.id.btnPaper);
        btnPaper.setOnClickListener(new ChoiceButtonListener(ivSelectedChoice, R.drawable.paper));

        Button btnScissors = view.findViewById(R.id.btnScissors);
        btnScissors.setOnClickListener(new ChoiceButtonListener(ivSelectedChoice, R.drawable.scissors));

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
}