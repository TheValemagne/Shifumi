package com.example.shifumi.game;

public class VictoryCondition {
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
