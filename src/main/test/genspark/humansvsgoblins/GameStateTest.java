package genspark.humansvsgoblins;

import genspark.humansvsgoblins.land.Coordinates;
import genspark.humansvsgoblins.land.MaxCoordinates;
import genspark.humansvsgoblins.pieces.Goblin;
import genspark.humansvsgoblins.pieces.Human;
import genspark.humansvsgoblins.pieces.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {
    Properties properties;
    Human human;
    Goblin goblin;

    @BeforeEach
    void setUp() {
        FileReader fileReader;
        try {
            fileReader = new FileReader("game.properties");
            properties = new Properties();
            properties.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Failed to open file");
            throw new RuntimeException(e);
        }
        MaxCoordinates.maxCols = 10;
        MaxCoordinates.maxRows = 20;
        human = new Human(new Coordinates(1, 1));
        goblin = new Goblin(new Coordinates(0, 0));
    }

    @Test
    void determineGameState() {
        GameState gameState = GameState.PLAYING;
        human.setHealth(10);
        goblin.setHealth(10);
        assertEquals(GameState.PLAYING, GameState.determineGameState(new Players(human, goblin),
                10, gameState));
        human.setHealth(-10);
        assertEquals(GameState.LOST, GameState.determineGameState(new Players(human, goblin),
                10, gameState));
        gameState = GameState.PLAYING;
        human.setHealth(10);
        assertEquals(GameState.DRAW, GameState.determineGameState(new Players(human, goblin),
                0, gameState));
        gameState = GameState.PLAYING;
        human.setHealth(10);
        goblin.setHealth(-10);
        assertEquals(GameState.WON, GameState.determineGameState(new Players(human, goblin),
                10, gameState));
    }
}