package com.example.shifumi.game;

/**
 * Conteneur pour une condition de victoire
 */
public final class VictoryCondition {
    public final Choice opponentChoice;
    public final Choice victoryChoice;

    public VictoryCondition(Choice opponentChoice, Choice victoryChoice) {
        this.opponentChoice = opponentChoice;
        this.victoryChoice = victoryChoice;
    }

    public boolean hasWon(Choice playerChoice) {
        return playerChoice.equals(victoryChoice);
    }

}
