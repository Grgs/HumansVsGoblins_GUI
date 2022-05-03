package genspark.humansvsgoblins;

import genspark.humansvsgoblins_gui.MainController;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static Properties getProperties() {
        Properties properties = null;
        try {
            FileReader fileReader = new FileReader("game.properties");
            properties = new Properties();
            properties.load(fileReader);
            fileReader.close();
            return properties;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
        return properties;
    }

    public static void initializePlayers(Properties properties, Goblin goblin, Human human) {
        goblin.setCoordinates(0, 0);
        goblin.setHealth(Integer.parseInt((String) properties.get("initialGoblinHealth")));
        goblin.setAttack(Integer.parseInt((String) properties.get("initialGoblinAttack")));
        human.setCoordinates(MaxCoordinates.maxCols / 2, MaxCoordinates.maxRows / 2);
        human.setHealth(Integer.parseInt((String) properties.get("initialHumanHealth")));
        human.setAttack(Integer.parseInt((String) properties.get("initialHumanAttack")));
    }

    public static GameState determineGameState(int turnsLeft, Goblin goblin, Human human, GameState gameState) {
        if (human.getHealth() <= 0) {
            gameState = GameState.LOST;
        } else if (goblin.getHealth() <= 0) {
            gameState = GameState.WON;
        } else if (turnsLeft <= 0) {
            gameState = GameState.DRAW;
        }
        return gameState;
    }

    public static void printEndGameMessage(GameState gameState) {
        switch (gameState) {
            case WON:
                System.out.println("You won!");
                break;
            case LOST:
                System.out.println("You lost!");
                break;
            case DRAW:
                System.out.println("You survived!");
                break;
        }
    }

    public static void main(String[] args) {
        MainController.mainGame();
    }

}
