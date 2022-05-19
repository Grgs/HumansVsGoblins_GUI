package genspark.humansvsgoblins;

public enum GameState {
    PLAYING, WON, LOST, DRAW;

    /**
     * @param turnsLeft Number of turns left for the game to end
     * @param goblin    The goblin player
     * @param human     The human player
     * @param gameState The state of the game
     * @return The state of the game given the current status.
     */
    public static GameState determineGameState(int turnsLeft, Goblin goblin, Human human, GameState gameState) {
        if (human.getHealth() <= 0) return LOST;
        if (goblin.getHealth() <= 0) return WON;
        if (turnsLeft <= 0) return DRAW;
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
     * @param gameState Current status of the game
     * @param turnsLeft Turns left until the game ends
     * @param human     Human player
     * @param goblin    Goblin player
     * @return The health, attack and defense values of human and goblin
     */
    public String getStatusText(GameState gameState, int turnsLeft, Human human, Goblin goblin) {
        String statusText = String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n", human.defaultShape,
                human.getHealth(), human.getAttack(), human.getDefence());
        statusText += String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n", goblin.defaultShape,
                goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
        statusText += String.format("%d turns left%n", turnsLeft);
        System.out.println(statusText);
        System.out.println(getEndGameMessage(gameState));
        statusText += getEndGameMessage(gameState);
        return statusText;
    }
}
