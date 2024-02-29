package com.example.shifumi.game;

/**
 * Conteneur pour une condition de victoire
 */
public final class VictoryCondition {
    public final Choice opponentChoice;
    public final Choice victoryChoice;

    /**
     * Conteneur pour une condition de victoire
     *
     * @param opponentChoice choix de l'adversaire
     * @param victoryChoice choix gagnant
     */
    public VictoryCondition(Choice opponentChoice, Choice victoryChoice) {
        this.opponentChoice = opponentChoice;
        this.victoryChoice = victoryChoice;
    }

    /**
     * Vérifie si le joueur a gagnée la manche
     *
     * @param playerChoice choix actuel du joueur
     * @return vrai si le joueur a gagnée cette manche, sinon faux
     */
    public boolean hasWon(Choice playerChoice) {
        return playerChoice.equals(victoryChoice);
    }

}
