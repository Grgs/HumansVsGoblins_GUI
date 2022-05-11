package genspark.humansvsgoblins_gui;

import genspark.humansvsgoblins.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Properties properties;
    Human human;
    Goblin goblin;
    Random random;

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
        random = new Random();
    }

    @Test
    void determineGameState() {
        GameState gameState = GameState.PLAYING;
        human.setHealth(10);
        goblin.setHealth(10);
        assertEquals(GameState.PLAYING, Main.determineGameState(10, goblin, human, gameState));
        human.setHealth(-10);
        assertEquals(GameState.LOST, Main.determineGameState(10, goblin, human, gameState));
        gameState = GameState.PLAYING;
        human.setHealth(10);
        assertEquals(GameState.DRAW, Main.determineGameState(0, goblin, human, gameState));
        gameState = GameState.PLAYING;
        human.setHealth(10);
        goblin.setHealth(-10);
        assertEquals(GameState.WON, Main.determineGameState(10, goblin, human, gameState));
    }

    @Test
    void drawLandInitial() {
    }

    @Test
    void drawLand() {
    }

    @Test
    void movePlayer() {
    }
}