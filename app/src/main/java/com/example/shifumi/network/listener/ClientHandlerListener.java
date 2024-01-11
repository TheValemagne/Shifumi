package com.example.shifumi.network.listener;

import com.example.shifumi.game.Choice;

public interface ClientHandlerListener {
    void onReceive(Choice choice);
}
