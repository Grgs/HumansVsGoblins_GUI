package genspark.humansvsgoblins;

public enum GameState {
    PLAYING, WON, LOST, DRAW;

    public static GameState determineGameState(int turnsLeft, Goblin goblin, Human human, GameState gameState) {
        if (human.getHealth() <= 0) {
            gameState = LOST;
        } else if (goblin.getHealth() <= 0) {
            gameState = WON;
        } else if (turnsLeft <= 0) {
            gameState = DRAW;
        }
        return gameState;
    }
}
