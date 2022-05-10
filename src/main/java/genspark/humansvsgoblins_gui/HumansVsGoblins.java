package genspark.humansvsgoblins_gui;

import genspark.humansvsgoblins.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class HumansVsGoblins extends Application {
    Properties properties;
    int turnsLeft;
    Land land;
    Goblin goblin;
    Human human;
    Random random;
    GameState gameState;
    ArrayList<Piece> lootList;
    Label[][] landNodes = null;
    String statusText = "";

    public static void main(String[] args) {
        launch();
    }

    private void drawLand(Label[][] landNodes) {
        for (int i = 0; i < MaxCoordinates.maxCols; i++) {
            for (int j = 0; j < MaxCoordinates.maxRows; j++) {
                Tile tile = land.getGrid(new Coordinates(i, j));
                landNodes[j][i].setText(tile.toString());
            }
        }
    }

    public void movePlayer(String key, Label[][] landNodes) {
        key = key.toLowerCase(Locale.ROOT);
        human.move(key);
        goblin.move(human, this.turnsLeft);
        if (land.getGrid(human.getCoordinates()).piece != null) {
            lootList = human.absorbLoot(lootList);
            land.setGrid(human.getCoordinates(), null);
        }
        if (human.getCoordinates().collidesWith(goblin.getCoordinates())) {
            human = goblin.combat(human, random, Float.parseFloat((String) properties.get("combatRandomness")));
            Loot lootDrop = new Loot(new Coordinates(goblin.getCoordinates()));
            while (lootDrop.getCoordinates().equals(human.getCoordinates()) ||
                    lootDrop.getCoordinates().equals(goblin.getCoordinates())) {
                lootDrop.moveEast();
            }
            lootDrop.setDefence(5);
            lootList.add(lootDrop);
        }

        if (human.getCoordinates().equals(goblin.getCoordinates())) {
            goblin.moveEast();
        }

        this.turnsLeft--;
        gameState = Main.determineGameState(this.turnsLeft, goblin, human, gameState);

        statusText = String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n", human,
                human.getHealth(), human.getAttack(), human.getDefence());
        statusText += String.format("%s: Health = %d\t Attack = %d\t Defence = %d%n", goblin,
                goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
        statusText += String.format("%d turns left%n", this.turnsLeft);
        System.out.println(statusText);

        if (gameState.equals(GameState.WON)) {
            goblin.shape = "  ";
        } else if (gameState.equals(GameState.LOST)) {
            human.shape = "  ";
        }

        this.land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
        drawLand(landNodes);
        System.out.println(this.land);

        System.out.println(Main.printEndGameMessage(gameState));
        statusText += Main.printEndGameMessage(gameState);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.properties = Main.getProperties();
        MaxCoordinates.getProperties();
        this.turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));

        this.land = new Land();
        this.goblin = new Goblin();
        this.human = new Human();
        this.random = new Random();

        Main.initializePlayers(properties, goblin, human);

        lootList = Loot.getLootList(random);
        gameState = GameState.PLAYING;

        FXMLLoader fxmlLoader = new FXMLLoader(HumansVsGoblins.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 550);

        System.out.printf("Human\tVs\tGoblin%n%s\t\tVs\t%s%n", human, goblin);
        Label topLabel = (Label) scene.lookup("#topLabel");
        Label bottomLabel = (Label) scene.lookup("#bottomLabel");
        topLabel.setText(String.format("Human\tVs\tGoblin%n%s\t\tVs\t%s%n type 'q' to quit or%n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right:%n", human, goblin));
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
        System.out.println(land);

        GridPane gridPane = (GridPane) scene.lookup("#landGrid");
        landNodes = new Label[MaxCoordinates.maxRows][MaxCoordinates.maxCols];
        for (int i = 0; i < MaxCoordinates.maxCols; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(20));
            for (int j = 0; j < MaxCoordinates.maxRows; j++) {
                Tile tile = land.getGrid(new Coordinates(i, j));
                Label l = new Label(tile.toString());
                l.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, new CornerRadii(3.0), new BorderWidths(0.5))));
                landNodes[j][i] = l;
                gridPane.add(landNodes[j][i], i, j);
            }
        }

        stage.setTitle("Humans Vs. Goblins");

        scene.setOnKeyPressed((KeyEvent key) -> {
            System.out.println(key.getCode().toString());
            if (gameState == GameState.PLAYING)
                movePlayer(key.getCode().toString(), landNodes);
            bottomLabel.setText(statusText);
        });
        stage.setScene(scene);
        stage.show();
    }
}