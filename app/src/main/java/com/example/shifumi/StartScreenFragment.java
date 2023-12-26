package com.example.shifumi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shifumi.p2p.PeerToPeerManager;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class StartScreenFragment extends Fragment {
    protected Button startButton;
    private PeerToPeerManager peerToPeerManager;

    public StartScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity main = (MainActivity) this.getActivity();
        peerToPeerManager = main.getPeerToPeerManager();

        startButton = main.findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonListener(main, peerToPeerManager));
    }
}