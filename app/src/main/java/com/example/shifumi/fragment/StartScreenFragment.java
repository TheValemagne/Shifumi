package com.example.shifumi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shifumi.MainActivity;
import com.example.shifumi.databinding.FragmentStartScreenBinding;
import com.example.shifumi.fragment.listener.StartButtonListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class StartScreenFragment extends Fragment {

    public StartScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentStartScreenBinding binding = FragmentStartScreenBinding.inflate(inflater, container, false);
        binding.startButton.setOnClickListener(new StartButtonListener((MainActivity) this.requireActivity()));

        return binding.getRoot();
    }

}