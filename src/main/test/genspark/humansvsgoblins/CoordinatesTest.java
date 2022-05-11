package genspark.humansvsgoblins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    Coordinates coordinates;

    @BeforeEach
    void setUp() {
        MaxCoordinates.maxCols = 20;
        MaxCoordinates.maxRows = 30;
        coordinates = new Coordinates(5, 10);
    }

    @Test
    void setXY() {
        coordinates.setXY(25, 34);
        assertEquals(5, coordinates.x);
        assertEquals(4, coordinates.y);
        coordinates.setXY(-10, -5);
        assertEquals(10, coordinates.x);
        assertEquals(25, coordinates.y);
        coordinates.setXY(-25, -34);
        assertEquals(15, coordinates.x);
        assertEquals(26, coordinates.y);
        coordinates.setXY(100, 100);
        assertEquals(0, coordinates.x);
        assertEquals(10, coordinates.y);
    }

    @Test
    void collidesWith() {
        Coordinates coordinates2 = new Coordinates(6, 10);
        assertTrue(coordinates2.collidesWith(coordinates));
        coordinates2 = new Coordinates(5, 11);
        assertTrue(coordinates2.collidesWith(coordinates));
        coordinates2 = new Coordinates(6, 11);
        assertTrue(coordinates2.collidesWith(coordinates));
        coordinates2 = new Coordinates(7, 10);
        assertFalse(coordinates2.collidesWith(coordinates));
        coordinates2 = new Coordinates(5, 10);
        assertTrue(coordinates2.collidesWith(coordinates));
        coordinates2 = new Coordinates(1, 1);
        assertFalse(coordinates2.collidesWith(coordinates));
        coordinates2 = new Coordinates(20, 20);
        assertFalse(coordinates2.collidesWith(coordinates));
    }

    @Test
    void testEquals() {
        Coordinates coordinates2 = new Coordinates(5, 10);
        assertTrue(coordinates2.equals(coordinates));
        coordinates2 = new Coordinates(6, 10);
        assertFalse(coordinates2.equals(coordinates));
        coordinates2 = new Coordinates(5, 11);
        assertFalse(coordinates2.equals(coordinates));
        coordinates2 = new Coordinates(1, 1);
        assertFalse(coordinates2.equals(coordinates));
    }
}