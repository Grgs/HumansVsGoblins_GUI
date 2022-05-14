package genspark.humansvsgoblins;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LandTest {

    private Button button;
    Land land;
    Human human;
    Goblin goblin;

//    @Override
//    public void start(Stage stage) {
//        button = new Button("click me!");
//        button.setOnAction(actionEvent -> button.setText("clicked!"));
//        stage.setScene(new Scene(new StackPane(button), 100, 100));
//        stage.show();
//    }
//    @Test
//    public void should_contain_button_with_text() {
//        FxAssert.verifyThat(".button", LabeledMatchers.hasText("click me!"));
//    }
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
//        Set<Tile> gridSet = land.grid.stream().flatMap(Collection::stream).collect(Collectors.toSet());
//        assertEquals(1, gridSet.size());
    }

//    @Test
//    void setGrid() {
//    }
//
//    @Test
//    void testSetGrid() {
//    }
//
//    @Test
//    void getGrid() {
//    }
//
//    @Test
//    void addPieces() {
//        human.setCoordinates(1, 2);
//        goblin.setCoordinates(2, 3);
//        land.addPieces(new ArrayList<>(List.of(new Piece[]{human, goblin})));
//        assertEquals(human.toString(), land.getGrid(new Coordinates(1, 2)).toString());
//        assertEquals(goblin.toString(), land.getGrid(new Coordinates(2, 3)).toString());
//        Set<Tile> gridSet = land.grid.stream().flatMap(Collection::stream).collect(Collectors.toSet());
//        assertEquals(3, gridSet.size());
//    }
//
//    @Test
//    void update() {
//        human.setCoordinates(1, 2);
//        goblin.setCoordinates(2, 3);
//        Loot loot = new Loot(new Coordinates(3, 4));
//        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})),
//                new ArrayList<>(List.of(new Loot[]{loot})));
//        assertEquals(human.toString(), land.getGrid(new Coordinates(1, 2)).toString());
//        assertEquals(goblin.toString(), land.getGrid(new Coordinates(2, 3)).toString());
//        assertEquals(loot.toString(), land.getGrid(new Coordinates(3, 4)).toString());
//        Set<Tile> gridSet = land.grid.stream().flatMap(Collection::stream).collect(Collectors.toSet());
//        assertEquals(4, gridSet.size());
//    }
}