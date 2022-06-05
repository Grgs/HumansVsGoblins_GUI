package genspark.humansvsgoblins;

import genspark.humansvsgoblins.land.Coordinates;
import genspark.humansvsgoblins.land.Land;
import genspark.humansvsgoblins.land.MaxCoordinates;
import genspark.humansvsgoblins.pieces.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Humans vs Goblins</h1>
 * Humans vs Goblins is a game where a human attempts to defeat a goblin.
 *
 * @author Michael Guirguis
 */
public class Main extends Application {
    /**
     * Game properties stored in a file.
     */
    Properties properties;
    /**
     * Playing surface where player and loot are to be displayed. Game map.
     */
    Land land;
    /**
     * Status of the game.
     */
    GameState gameState;
    /**
     * A list of loot available on the land.
     */
    ArrayList<Piece> lootList;

    /**
     * Start the game.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Get the game properties from a file.
     *
     * @return Game properties from file.
     */
    private static Properties getProperties() {
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

    /**
     * Initialize the JavaFx scene.
     *
     * @return Main window scene.
     */
    private Scene initializeScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 650, 550);
        } catch (Exception e) {
            System.out.println("Could not open main-view.fxml\n" + e.getLocalizedMessage());
            System.exit(1);
        }
        return scene;
    }

    /**
     * Main game loop that runs when keys are pressed.
     *
     * @param scene          JavaFx scene.
     * @param turnsRemaining Number of turns remaining.
     * @param players        Players on the land.
     * @param bottomLabel    Label displaying game information at the bottom of the screen.
     */
    private void gameLoop(Scene scene, AtomicInteger turnsRemaining, Players players, Label bottomLabel) {
        scene.setOnKeyPressed((KeyEvent key) -> {
            if (gameState == GameState.PLAYING) {
                players.human.move(key.getCode().toString().toLowerCase(Locale.ROOT));
                if (land.getGrid(players.getHuman()).piece != null) {
                    lootList = players.human.absorbLoot(lootList);
                }
                players.goblin.move(players.human, turnsRemaining.get());
                if (players.human.getCoordinates().collidesWith(players.goblin.getCoordinates())) {
                    players.combat(properties, lootList);
                }
                players.deStackPlayers();

                gameState = GameState.determineGameState(players, turnsRemaining.get(), gameState);
                players.removeLosingPlayer(gameState);
                this.land.update(players, lootList);
                System.out.println(this.land);
                turnsRemaining.getAndDecrement();
            }
            bottomLabel.setText(gameState.getStatusText(players, turnsRemaining.get(), gameState));
            if (key.getCode().toString().toLowerCase(Locale.ROOT).equals("q"))
                System.exit(0);
        });
    }

    /**
     * Initialize and run the game.
     *
     * @param stage JavaFx stage
     */
    @Override
    public void start(Stage stage) {
        Scene scene = initializeScene();
        this.properties = getProperties();
        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));
        AtomicInteger turnsRemaining = new AtomicInteger(Integer.parseInt((String) properties.get("maxTurns")));

        this.land = new Land();
        Players players = new Players(
                new Human(new Coordinates(MaxCoordinates.maxCols / 2, MaxCoordinates.maxRows / 2), properties),
                new Goblin(new Coordinates(0, 0), properties));
        lootList = Loot.getLootList();
        gameState = GameState.PLAYING;

        ((Label) scene.lookup("#topLabel")).setText(
                String.format("Humans\tVs\tGoblins%n%s\t\tVs\t%s%n", players.getHuman(), players.getGoblin()));
        Label bottomLabel = (Label) scene.lookup("#bottomLabel");
        bottomLabel.setText("type 'q' to quit or%n\" +\n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right");
        land.update(players, lootList);

        GridPane gridPane = (GridPane) scene.lookup("#landGrid");
        land.setInitialLand(gridPane);
        stage.setTitle("Humans Vs. Goblins");

        gameLoop(scene, turnsRemaining, players, bottomLabel);
        stage.setScene(scene);
        stage.show();
    }
}