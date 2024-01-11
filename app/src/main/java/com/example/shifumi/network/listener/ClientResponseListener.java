package com.example.shifumi.network.listener;

import android.os.Bundle;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.GameFragment;
import com.example.shifumi.game.Choice;

public class ClientResponseListener implements ClientListener {
    private final MainActivity mainActivity;

    public ClientResponseListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Choice ownChoice, Choice opponentChoice) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(GameFragment.ARG_PARAM1, ownChoice);
        bundle.putSerializable(GameFragment.ARG_PARAM2, opponentChoice);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(bundle);

        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }
}
