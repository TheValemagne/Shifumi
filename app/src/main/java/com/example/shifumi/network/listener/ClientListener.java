package com.example.shifumi.network.listener;

import com.example.shifumi.game.Choice;

public interface ClientListener {
    void onReceive(Choice choice);
}
