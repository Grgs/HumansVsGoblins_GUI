package genspark.humansvsgoblins;

public enum GameState {
    PLAYING, WON, LOST, DRAW;

    /**
     * @param players        human and goblin players
     * @param turnsRemaining Number of turns left for the game to end
     * @return The state of the game given the current status.
     */
    public static GameState determineGameState(Players players, int turnsRemaining, GameState gameState) {
        if (players.getHuman().getHealth() <= 0) return LOST;
        if (players.getGoblin().getHealth() <= 0) return WON;
        if (turnsRemaining <= 0) return DRAW;
        return gameState;
    }

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
     * @param players        human and goblin players
     * @param turnsRemaining Turns left until the game ends
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
