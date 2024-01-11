package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.EnumMap;
import java.util.Map;

public abstract class ClientBase extends Thread{
    protected final Socket socket;
    protected final ObjectOutputStream outgoingFlow;
    protected final ObjectInputStream incomingFlow;

    private enum ChoiceIndex {
        OwnChoice,
        OpponentChoice
    }

    private final Map<ChoiceIndex, Choice> choices;
    protected final Object ownChoiceLock = new Object();
    protected final Object opponentChoiceLock = new Object();

    public ClientBase(Socket socket) throws IOException {
        this.socket = socket;
        this.outgoingFlow = new ObjectOutputStream(socket.getOutputStream());
        this.incomingFlow = new ObjectInputStream(socket.getInputStream());

        this.choices = new EnumMap<>(ChoiceIndex.class);
        resetChoices();
    }

    public Choice getOwnChoice() {
        return choices.get(ChoiceIndex.OwnChoice);
    }

    public void setOwnChoice(Choice choice) {
        synchronized (ownChoiceLock) {
            Log.d("ClientBase", "setOwnChoice");
            choices.put(ChoiceIndex.OwnChoice, choice);
            ownChoiceLock.notifyAll();
        }
    }

    public Choice getOpponentChoice() {
        return choices.get(ChoiceIndex.OpponentChoice);
    }

    public void setOpponentChoice(Choice choice) {
        synchronized (opponentChoiceLock) {
            choices.put(ChoiceIndex.OpponentChoice, choice);
            opponentChoiceLock.notify();
        }
    }

    protected void resetChoices() {
        setOwnChoice(Choice.UNSET);
        setOpponentChoice(Choice.UNSET);
    }

    public void close() throws IOException {
        this.incomingFlow.close();
        this.outgoingFlow.close();
        this.socket.close();
        this.interrupt();
    }
}
