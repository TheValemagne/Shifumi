package com.example.shifumi.network.listener;

import com.example.shifumi.MainActivity;
import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;

public class ClientResponseListener implements ClientListener {
    private final MainActivity mainActivity;

    public ClientResponseListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Choice opponentChoice) {
        // TODO update UI + use of game
        Game game = mainActivity.getGame();
    }
}
