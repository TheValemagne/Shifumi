package com.example.shifumi.network.listener;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.EndgameFragment;
import com.example.shifumi.fragment.PlayFragment;

public class ClientRoundListener {
    private final MainActivity mainActivity;

    public ClientRoundListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onNext() {
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new PlayFragment())
                .commit();
    }

    public void onEnd() {
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new EndgameFragment())
                .commit();
    }
}
