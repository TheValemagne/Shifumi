package com.example.shifumi.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class Game {
    private enum ScoreKey {
        PLAYER,
        OPPONENT
    }

    private final Map<ScoreKey, Integer> scores;

    /**
     * Retourne le score actuel du joueur
     *
     * @return score du joueur
     */
    public int getPlayerScore() {
        return scores.get(ScoreKey.PLAYER);
    }

    /**
     * Retourne le score actuel de l'opposent
     *
     * @return score de l'opposent
     */
    public int getOpponentScore() {
        return scores.get(ScoreKey.OPPONENT);
    }

    private final List<VictoryCondition> victoryConditions;

    /**
     * Réinitialise le score des deux joueurs
     */
    public Game() {
        scores = new EnumMap<>(ScoreKey.class);
        resetScores();

        victoryConditions = new ArrayList<>(Arrays.asList(
                        new VictoryCondition(Choice.ROCK, Choice.PAPER),
                        new VictoryCondition(Choice.PAPER, Choice.SCISSORS),
                        new VictoryCondition(Choice.SCISSORS, Choice.ROCK)));
    }

    /**
     * Convertir un booléen en résultat
     *
     * @param hasWon vrai si le joueur a gangé la manche
     * @return l'enum Result correspondant
     */
    private Result resultMapper(boolean hasWon) {
        return hasWon ? Result.WIN : Result.LOST;
    }

    /**
     * Vérifie si le joueur a gangé la manche
     *
     * @param playerChoice   choix sélectionné par le joueur
     * @param opponentChoice choix sélectionné par l'opposent
     * @return le résultat de la manche : WIN pour gagner, LOST pour perdu ou DRAW pour match null
     */
    public Result hasWon(Choice playerChoice, Choice opponentChoice) {
        if (playerChoice.equals(opponentChoice)) {
            return Result.DRAW; // match nul
        }

        for (VictoryCondition victoryCondition : victoryConditions) {
            if (victoryCondition.opponentChoice.equals(opponentChoice)) {
                return resultMapper(victoryCondition.hasWon(playerChoice));
            }
        }

        return Result.LOST;
    }

    public void updateScore(Result result) {
        if (result.equals(Result.DRAW)) {
            return;
        }

        ScoreKey key = result.equals(Result.WIN) ? ScoreKey.PLAYER : ScoreKey.OPPONENT;
        scores.put(key, scores.get(key) + 1);
    }

    public void resetScores() {
        for (ScoreKey key : ScoreKey.values()) {
            scores.put(key, 0);
        }
    }
}
