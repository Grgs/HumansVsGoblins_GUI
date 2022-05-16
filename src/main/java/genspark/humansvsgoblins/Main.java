package genspark.humansvsgoblins;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    Properties properties;
    Land land;
    GameState gameState;
    ArrayList<Piece> lootList;

    public static void main(String[] args) {
        launch();
    }

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

    private void combat(Human human, Goblin goblin) {
        if (human.getCoordinates().collidesWith(goblin.getCoordinates())) {
            human = goblin.combat(human, Float.parseFloat((String) properties.get("combatRandomness")));
            Loot lootDrop = new Loot(new Coordinates(goblin.getCoordinates()));
            int n = 0;
            while ((lootDrop.getCoordinates().equals(human.getCoordinates()) ||
                    lootDrop.getCoordinates().equals(goblin.getCoordinates())) &&
                    n < 3) {
                lootDrop.moveEast();
                n++;
            }
            lootDrop.setDefence(5);
            lootList.add(lootDrop);
        }
    }

    private void removeLosingPlayer(Goblin goblin, Human human) {
        if (gameState.equals(GameState.WON)) {
            goblin.shape = "  ";
        } else if (gameState.equals(GameState.LOST)) {
            human.shape = "  ";
        }
    }

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

        this.properties = getProperties();
        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));
        AtomicInteger turnsLeft = new AtomicInteger(Integer.parseInt((String) properties.get("maxTurns")));

        this.land = new Land();
        Goblin goblin = new Goblin(new Coordinates(0, 0), properties);
        Human human = new Human(new Coordinates(MaxCoordinates.maxCols / 2,
                MaxCoordinates.maxRows / 2), properties);
        lootList = Loot.getLootList();
        gameState = GameState.PLAYING;

        Label topLabel = (Label) scene.lookup("#topLabel");
        Label bottomLabel = (Label) scene.lookup("#bottomLabel");
        topLabel.setText(String.format("Humans\tVs\tGoblins%n%s\t\tVs\t%s%n", human, goblin));
        bottomLabel.setText("type 'q' to quit or%n\" +\n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right");
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
        System.out.println(land);

        GridPane gridPane = (GridPane) scene.lookup("#landGrid");
        land.setInitialLand(gridPane);
        stage.setTitle("Humans Vs. Goblins");

        scene.setOnKeyPressed((KeyEvent key) -> {
            if (gameState == GameState.PLAYING) {
                human.move(key.getCode().toString().toLowerCase(Locale.ROOT));
                if (land.getGrid(human.getCoordinates()).piece != null) {
                    lootList = human.absorbLoot(lootList);
                }
                goblin.move(human, turnsLeft.get());
                combat(human, goblin);
                goblin.deStackPlayers(human);

                gameState = GameState.determineGameState(turnsLeft.get(), goblin, human, gameState);
                removeLosingPlayer(goblin, human);
                this.land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
                System.out.println(this.land);
                turnsLeft.getAndDecrement();
            }
            bottomLabel.setText(gameState.getStatusText(gameState, turnsLeft.get(), human, goblin));
            if (key.getCode().toString().toLowerCase(Locale.ROOT).equals("q"))
                System.exit(0);
        });
        stage.setScene(scene);
        stage.show();
    }
}