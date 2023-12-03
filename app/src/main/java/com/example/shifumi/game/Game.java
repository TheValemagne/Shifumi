package com.example.shifumi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final Random random = new Random();
    private int playerScore;

    public int getPlayerScore() {
        return playerScore;
    }

    private int opponentScore;

    public int getOpponentScore() {
        return opponentScore;
    }

    private final List<VictoryCondition> victoryConditions;

    public Game() {
        resetScore();

        victoryConditions = new ArrayList<>();
        victoryConditions.add(new VictoryCondition(Choice.ROCK, Choice.PAPER));
        victoryConditions.add(new VictoryCondition(Choice.PAPER, Choice.SCISSORS));
        victoryConditions.add(new VictoryCondition(Choice.SCISSORS, Choice.ROCK));
    }

    public static Choice getRandomChoice() {
        return Choice.values()[random.nextInt(Choice.values().length)];
    }

    private Result resultMapper(boolean hasWon) {
        return hasWon ? Result.WIN : Result.LOST;
    }

    public Result hasWon(Choice playerChoice, Choice opponentChoice) {
        if (playerChoice.equals(opponentChoice)){
            return Result.DRAW; // match nul
        }

        for (VictoryCondition victoryCondition : victoryConditions) {
            if(victoryCondition.opponentChoice.equals(opponentChoice)) {
                return resultMapper(victoryCondition.hasWon(playerChoice));
            }
        }

        return Result.LOST;
    }

    public void updateScore(Result result) {
        if (result.equals(Result.WIN)) {
            playerScore++;
        } else if (result.equals(Result.LOST)) {
            opponentScore++;
        }
    }

    public void resetScore() {
        this.playerScore = 0;
        this.opponentScore = 0;
    }
}
