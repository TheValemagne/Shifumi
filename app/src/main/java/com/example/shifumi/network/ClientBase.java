package com.example.shifumi.network;

import com.example.shifumi.game.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.EnumMap;
import java.util.Map;

/**
 * Classe abtraite des clients et interlocuteurs
 */
public abstract class ClientBase extends Thread{
    protected final Socket socket;

    public ObjectOutputStream getOutgoingFlow() {
        return outgoingFlow;
    }

    protected final ObjectOutputStream outgoingFlow;
    protected final ObjectInputStream incomingFlow;

    private enum ChoiceIndex {
        OwnChoice,
        OpponentChoice
    }

    private final Map<ChoiceIndex, Choice> choices;
    protected final Object ownChoiceLock = new Object();
    protected final Object opponentChoiceLock = new Object();

    public Object getOpponentChoiceLock() {
        return opponentChoiceLock;
    }

    /**
     * Classe abtraite des clients et interlocuteurs
     *
     * @param socket socket de la communication client / serveur
     * @throws IOException erreur lors de l'ouverture des cannaux de communication
     */
    public ClientBase(Socket socket) throws IOException {
        this.socket = socket;
        this.outgoingFlow = new ObjectOutputStream(socket.getOutputStream());
        this.incomingFlow = new ObjectInputStream(socket.getInputStream());

        this.choices = new EnumMap<>(ChoiceIndex.class);
        resetChoices();
    }

    /**
     * Retourne le choix du joueur actuel
     *
     * @return choix du joueur
     */
    public Choice getOwnChoice() {
        return choices.get(ChoiceIndex.OwnChoice);
    }

    /**
     * Modifier le choix du joueur actuel
     *
     * @param choice nouveau choix du joueur
     */
    public void setOwnChoice(Choice choice) {
        synchronized (ownChoiceLock) {
            choices.put(ChoiceIndex.OwnChoice, choice);
            ownChoiceLock.notifyAll();
        }
    }

    /**
     * Retourne le choix du joueur opposant
     *
     * @return choix du joueur
     */
    public Choice getOpponentChoice() {
        return choices.get(ChoiceIndex.OpponentChoice);
    }

    /**
     * Modifier le choix du joueur actuel
     *
     * @param choice nouveau choix du joueur
     */
    public void setOpponentChoice(Choice choice) {
        synchronized (opponentChoiceLock) {
            choices.put(ChoiceIndex.OpponentChoice, choice);
            opponentChoiceLock.notify();
        }
    }

    /**
     * Remise à zéro des choix
     */
    public void resetChoices() {
        setOwnChoice(Choice.UNSET);
        setOpponentChoice(Choice.UNSET);
    }

    /**
     * Fermeture de la connexion
     *
     * @throws IOException problèmes lors de la fermeture
     */
    public void close() throws IOException {
        this.interrupt();
        this.incomingFlow.close();
        this.outgoingFlow.close();
        this.socket.close();
    }
}
