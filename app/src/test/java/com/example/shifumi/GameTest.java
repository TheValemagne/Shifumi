package com.example.shifumi;

import static org.junit.Assert.assertEquals;

import com.example.shifumi.game.Choice;
import com.example.shifumi.game.Game;
import com.example.shifumi.game.Result;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameTest {
    public static Game game;

    @BeforeClass
    public static void setup() {
        game = new Game();
    }

    @Test
    public void shouldWin() {
        Result result1 = game.hasWon(Choice.PAPER, Choice.ROCK);
        Result result2 = game.hasWon(Choice.SCISSORS, Choice.PAPER);
        Result result3 = game.hasWon(Choice.ROCK, Choice.SCISSORS);

        assertEquals(Result.WIN, result1);
        assertEquals(Result.WIN, result2);
        assertEquals(Result.WIN, result3);
    }

    @Test
    public void shouldLost() {
        Result result1 = game.hasWon(Choice.ROCK, Choice.PAPER);
        Result result2 = game.hasWon(Choice.PAPER, Choice.SCISSORS);
        Result result3 = game.hasWon(Choice.SCISSORS, Choice.ROCK);

        assertEquals(Result.LOST, result1);
        assertEquals(Result.LOST, result2);
        assertEquals(Result.LOST, result3);
    }

    @Test
    public void shouldDraw() {
        Result result1 = game.hasWon(Choice.PAPER, Choice.PAPER);
        Result result2 = game.hasWon(Choice.SCISSORS, Choice.SCISSORS);
        Result result3 = game.hasWon(Choice.ROCK, Choice.ROCK);

        assertEquals(Result.DRAW, result1);
        assertEquals(Result.DRAW, result2);
        assertEquals(Result.DRAW, result3);
    }

    @Test
    public void shouldUpgradePlayerScore() {
        int oldPlayerScore = game.getPlayerScore();
        int oldOpponentScore = game.getOpponentScore();

        game.updateScore(Result.WIN);

        assertEquals(oldPlayerScore + 1, game.getPlayerScore());
        assertEquals(oldOpponentScore, game.getOpponentScore());
    }

    @Test
    public void shouldUpgradeOpponentScore() {
        int oldPlayerScore = game.getPlayerScore();
        int oldOpponentScore = game.getOpponentScore();

        game.updateScore(Result.LOST);

        assertEquals(oldPlayerScore, game.getPlayerScore());
        assertEquals(oldOpponentScore + 1, game.getOpponentScore());
    }

    @Test
    public void shouldUpgradeNothing() {
        int oldPlayerScore = game.getPlayerScore();
        int oldOpponentScore = game.getOpponentScore();

        game.updateScore(Result.DRAW);

        assertEquals(oldPlayerScore, game.getPlayerScore());
        assertEquals(oldOpponentScore, game.getOpponentScore());
    }
}
