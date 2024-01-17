package com.example.shifumi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shifumi.MainActivity;
import com.example.shifumi.databinding.FragmentEndgameBinding;
import com.example.shifumi.fragment.listener.ExitButtonListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EndgameFragment extends Fragment {

    public EndgameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEndgameBinding binding = FragmentEndgameBinding.inflate(inflater, container, false);

        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.getPeerToPeerManager().disconnect();

        binding.endgameScore.setText(mainActivity.getScoreMsg());
        binding.quitAppBtn.setOnClickListener(new ExitButtonListener(mainActivity));

        return binding.getRoot();
    }
}