package genspark.humansvsgoblins;

import genspark.humansvsgoblins_gui.Main;

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

    public String getStatusText(GameState gameState, int turnsLeft, Human human, Goblin goblin) {
        String statusText = String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n", human,
                human.getHealth(), human.getAttack(), human.getDefence());
        statusText += String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n", goblin,
                goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
        statusText += String.format("%d turns left%n", turnsLeft);
        System.out.println(statusText);
        System.out.println(getEndGameMessage(gameState));
        statusText += getEndGameMessage(gameState);
        return statusText;
    }
}
