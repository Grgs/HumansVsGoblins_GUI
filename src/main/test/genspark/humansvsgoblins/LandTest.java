package genspark.humansvsgoblins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LandTest {

    Land land;
    Human human;
    Goblin goblin;

    @BeforeEach
    void setUp() {
        MaxCoordinates.maxCols = 20;
        MaxCoordinates.maxRows = 30;
        land = new Land();
        human = new Human(new Coordinates(1, 1));
        goblin = new Goblin(new Coordinates(0, 0));
    }

    @Test
    void emptyGrid() {
        assertEquals(30, land.grid.size());
        assertEquals(20, land.grid.get(0).size());
        Set<Tile> gridSet = land.grid.stream().flatMap(Collection::stream).collect(Collectors.toSet());
        assertEquals(1, gridSet.size());
    }

    @Test
    void setGrid() {
    }

    @Test
    void testSetGrid() {
    }

    @Test
    void getGrid() {
    }

    @Test
    void addPieces() {
        human.setCoordinates(1, 2);
        goblin.setCoordinates(2, 3);
        land.addPieces(new ArrayList<>(List.of(new Piece[]{human, goblin})));
        assertEquals(human.toString(), land.getGrid(new Coordinates(1, 2)).toString());
        assertEquals(goblin.toString(), land.getGrid(new Coordinates(2, 3)).toString());
        Set<Tile> gridSet = land.grid.stream().flatMap(Collection::stream).collect(Collectors.toSet());
        assertEquals(3, gridSet.size());
    }

    @Test
    void update() {
        human.setCoordinates(1, 2);
        goblin.setCoordinates(2, 3);
        Loot loot = new Loot(new Coordinates(3, 4));
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})),
                new ArrayList<>(List.of(new Loot[]{loot})));
        assertEquals(human.toString(), land.getGrid(new Coordinates(1, 2)).toString());
        assertEquals(goblin.toString(), land.getGrid(new Coordinates(2, 3)).toString());
        assertEquals(loot.toString(), land.getGrid(new Coordinates(3, 4)).toString());
        Set<Tile> gridSet = land.grid.stream().flatMap(Collection::stream).collect(Collectors.toSet());
        assertEquals(4, gridSet.size());
    }
}