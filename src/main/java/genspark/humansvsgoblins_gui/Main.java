package genspark.humansvsgoblins_gui;

import genspark.humansvsgoblins.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.*;

public class Main extends Application {
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

    public void initializePlayers(Properties properties) {
        goblin.setHealth(Integer.parseInt((String) properties.get("initialGoblinHealth")));
        goblin.setAttack(Integer.parseInt((String) properties.get("initialGoblinAttack")));
        human.setHealth(Integer.parseInt((String) properties.get("initialHumanHealth")));
        human.setAttack(Integer.parseInt((String) properties.get("initialHumanAttack")));
    }

    public static GameState determineGameState(int turnsLeft, Goblin goblin, Human human, GameState gameState) {
        if (human.getHealth() <= 0) {
            gameState = GameState.LOST;
        } else if (goblin.getHealth() <= 0) {
            gameState = GameState.WON;
        } else if (turnsLeft <= 0) {
            gameState = GameState.DRAW;
        }
        return gameState;
    }

    private static String getEndGameMessage(GameState gameState) {
        switch (gameState) {
            case WON:
                return ("You Won!");
            case LOST:
                return ("You Lost!");
            case DRAW:
                return ("You Survived!");
        }
        return "";
    }

    public void drawLandInitial(GridPane gridPane) {
        landNodes = new Label[MaxCoordinates.maxRows][MaxCoordinates.maxCols];
        for (int i = 0; i < MaxCoordinates.maxCols; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(20));
            for (int j = 0; j < MaxCoordinates.maxRows; j++) {
                Tile tile = land.getGrid(new Coordinates(i, j));
                Label l = new Label(tile.toString());
                l.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID,
                        new CornerRadii(3.0), new BorderWidths(0.5))));
                l.setAlignment(Pos.CENTER);
                landNodes[j][i] = l;
                gridPane.add(landNodes[j][i], i, j);
            }
        }
    }

    public void drawLand(Label[][] landNodes) {
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
        gameState = determineGameState(this.turnsLeft, goblin, human, gameState);

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
        System.out.println(getEndGameMessage(gameState));
        statusText += getEndGameMessage(gameState);
    }

    @Override
    public void start(Stage stage) {
        this.properties = getProperties();
        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));
        this.turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));

        this.land = new Land();
        this.goblin = new Goblin(new Coordinates(0, 0));
        this.human = new Human(new Coordinates(MaxCoordinates.maxCols/2, MaxCoordinates.maxRows/2));
        this.random = new Random();

        initializePlayers(properties);

        lootList = Loot.getLootList(random);
        gameState = GameState.PLAYING;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 650, 550);
        } catch (Exception e){
            System.out.println("Could not open main-view.fxml\n" + e.getLocalizedMessage());
            System.exit(1);
        }

        System.out.printf("Human\tVs\tGoblin%n%s\t\tVs\t%s%n", human, goblin);
        Label topLabel = (Label) scene.lookup("#topLabel");
        Label bottomLabel = (Label) scene.lookup("#bottomLabel");
        topLabel.setText(String.format("Human\tVs\tGoblin%n%s\t\tVs\t%s%n", human, goblin));
        bottomLabel.setText("type 'q' to quit or%n\" +\n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right");
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
        System.out.println(land);

        GridPane gridPane = (GridPane) scene.lookup("#landGrid");
        drawLandInitial(gridPane);
        stage.setTitle("Humans Vs. Goblins");

        scene.setOnKeyPressed((KeyEvent key) -> {
            if (gameState == GameState.PLAYING)
                movePlayer(key.getCode().toString(), landNodes);
            bottomLabel.setText(statusText);
            if (key.getCode().toString().toLowerCase(Locale.ROOT).equals("q"))
                System.exit(0);
        });
        stage.setScene(scene);
        stage.show();
    }
}