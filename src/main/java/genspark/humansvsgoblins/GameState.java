package genspark.humansvsgoblins;

import genspark.humansvsgoblins.pieces.Players;

/**
 * State of the game.
 */
public enum GameState {
    /**
     * The game is still in progress.
     */
    PLAYING,
    /**
     * The human player has won.
     */
    WON,
    /**
     * The goblin player has won and the human player has lost.
     */
    LOST,
    /**
     * The maximum number of turns has elapsed but no player has won.
     */
    DRAW;

    /**
     * returns the game state based on the current health of the players and the remaining turns.
     *
     * @param players        human and goblin players
     * @param turnsRemaining Number of turns left for the game to end
     * @param gameState      Current state of the game
     * @return The state of the game given the current status.
     */
    public static GameState determineGameState(Players players, int turnsRemaining, GameState gameState) {
        if (players.getHuman().getHealth() <= 0) return LOST;
        if (players.getGoblin().getHealth() <= 0) return WON;
        if (turnsRemaining <= 0) return DRAW;
        return gameState;
    }

    /**
     * returns the game state based on the current health of the players and the remaining turns.
     *
     * @param gameState State of the game
     * @return A string telling the user what the game state is at the end of the game.
     */
    public static String getEndGameMessage(GameState gameState) {
        switch (gameState) {
            case WON:
                return ("You Won!");
            case LOST:
                return ("You Lost!");
            case DRAW:
                return ("You Survived!");
        }
        return "";
    }

    /**
     * Get text representing the status to the players
     *
     * @param players        human and goblin players
     * @param turnsRemaining Turns left until the game ends
     * @param gameState      Current state of the game
     * @return The health, attack and defense values of human and goblin
     */
    public String getStatusText(Players players, int turnsRemaining, GameState gameState) {
        String statusText = String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n",
                players.getHuman().defaultShape, players.getHuman().getHealth(),
                players.getHuman().getAttack(), players.getHuman().getDefence());
        statusText += String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n",
                players.getGoblin().defaultShape, players.getGoblin().getHealth(),
                players.getGoblin().getAttack(), players.getGoblin().getDefence());
        statusText += String.format("%d turns left%n", turnsRemaining);
        System.out.println(statusText);
        System.out.println(getEndGameMessage(gameState));
        statusText += getEndGameMessage(gameState);
        return statusText;
    }
}
