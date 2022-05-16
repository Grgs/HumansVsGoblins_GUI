package genspark.humansvsgoblins;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LandTest extends ApplicationTest {

    Land land;
    Human human;
    Goblin goblin;

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 650, 550);
        } catch (Exception e) {
            System.out.println("Could not open main-view.fxml\n" + e.getLocalizedMessage());
            System.exit(1);
        }
        stage.setScene(scene);
    }

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
        Set<String> gridSet = land.grid.stream().flatMap(Collection::stream).map(l -> l.label.getText()).
                collect(Collectors.toSet());
        assertEquals(1, gridSet.size());
    }

    @Test
    void addPieces() {
        human.setCoordinates(1, 2);
        goblin.setCoordinates(2, 3);
        land.addPieces(new ArrayList<>(List.of(new Piece[]{human, goblin})));
        assertEquals(human.toString(), land.getGrid(new Coordinates(1, 2)).toString());
        assertEquals(goblin.toString(), land.getGrid(new Coordinates(2, 3)).toString());
        Set<String> gridSet = land.grid.stream().flatMap(Collection::stream).map(l -> l.label.getText()).
                collect(Collectors.toSet());
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
        Set<String> gridSet = land.grid.stream().flatMap(Collection::stream).map(l -> l.label.getText()).
                collect(Collectors.toSet());
        assertEquals(4, gridSet.size());
    }
}