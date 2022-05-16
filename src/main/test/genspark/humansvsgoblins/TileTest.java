package genspark.humansvsgoblins;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest extends ApplicationTest {

    Tile tile;

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

        tile = new Tile();
    }

    @Test
    void setTile() {
        Piece piece = new Piece();
        piece.shape = "s";
        tile.setTile(piece);
        assertEquals("s", tile.label.getText());
    }
}