package genspark.humansvsgoblins_gui;

import genspark.humansvsgoblins.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
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
    public void movePlayer(String key) {
        while (gameState == GameState.PLAYING) {
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

            System.out.printf("%s: Health = %d\t Attack = %d\t Defence = %d%n", human,
                    human.getHealth(), human.getAttack(), human.getDefence());
            System.out.printf("%s: Health = %d\t Attack = %d\t Defence = %d%n", goblin,
                    goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
            System.out.printf("%d turns left%n", this.turnsLeft);

            if (gameState.equals(GameState.WON)) {
                goblin.shape = "  ";
            } else if (gameState.equals(GameState.LOST)) {
                human.shape = "  ";
            }

            this.land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
            System.out.println(this.land);

            Main.printEndGameMessage(gameState);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.properties = Main.getProperties();
        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));
        this.turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));

        this.land = new Land();
        this.goblin = new Goblin();
        this.human = new Human();
        Scanner scanner = new Scanner(System.in);
        this.random = new Random();

        Main.initializePlayers(properties, goblin, human);

        lootList = Loot.getLootList(random);
        gameState = GameState.PLAYING;

        System.out.printf("Human\tVs\tGoblin%n%s\t\tVs\t%s%n", human, goblin);
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);

        System.out.println(land);

        System.out.println("type 'q' to quit or\n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right \nthen press enter:");

//        movePlayer(scanner);
        FXMLLoader fxmlLoader = new FXMLLoader(HumansVsGoblins.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Humans Vs. Goblins");

        Label label = new Label("X");

        scene.setOnKeyPressed((KeyEvent key) -> {
            System.out.println(key.getCode().toString());
            switch (key.getCode().toString()){
                case "a": System.out.println("left");
            }
        });
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}