package genspark.humansvsgoblins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoblinTest {
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
        MaxCoordinates.maxCols = 25;
        MaxCoordinates.maxRows = 40;
        human = new Human(new Coordinates(1, 1));
        goblin = new Goblin(new Coordinates(0, 0));
        random = new Random();
    }

    @Test
    void combat() {
        assertDoesNotThrow(() -> goblin.combat(human, 1.5F));
        goblin.setAttack(10);
        human.setAttack(10);
        int initialHumanHealth = human.getHealth();
        human = goblin.combat(human, 0F);
        assertTrue(initialHumanHealth > human.getHealth());
    }

    @Test
    void move() {
        MaxCoordinates.maxCols = 100;
        MaxCoordinates.maxRows = 100;
        goblin.setCoordinates(3, 3);
        human.setCoordinates(90, 90);
        double initialDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        goblin.move(human, 99);
        double currentDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        assertTrue(initialDistance > currentDistance);
        goblin.setCoordinates(10, 10);
        human.setCoordinates(20, 20);
        initialDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        goblin.move(human, 1);
        currentDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        assertTrue(initialDistance > currentDistance);
    }
}