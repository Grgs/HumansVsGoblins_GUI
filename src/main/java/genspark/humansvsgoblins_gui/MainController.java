package genspark.humansvsgoblins_gui;

import genspark.humansvsgoblins.*;
import javafx.fxml.FXML;

import java.util.*;

public class MainController {
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

    public static void mainGame() {
        Properties properties = Main.getProperties();
        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));
        int turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));

        Land land = new Land();
        Goblin goblin = new Goblin();
        Human human = new Human();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Main.initializePlayers(properties, goblin, human);

        ArrayList<Piece> lootList = Loot.getLootList(random);
        GameState gameState = GameState.PLAYING;

        System.out.printf("Human\tVs\tGoblin%n%s\t\tVs\t%s%n", human, goblin);
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);

        System.out.println(land);
        System.out.println("type 'q' to quit or\n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right \nthen press enter:");

        while (gameState == GameState.PLAYING) {
            human.move(scanner);
            goblin.move(human, turnsLeft);
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

            turnsLeft--;
            gameState = Main.determineGameState(turnsLeft, goblin, human, gameState);

            System.out.printf("%s: Health = %d\t Attack = %d\t Defence = %d%n", human,
                    human.getHealth(), human.getAttack(), human.getDefence());
            System.out.printf("%s: Health = %d\t Attack = %d\t Defence = %d%n", goblin,
                    goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
            System.out.printf("%d turns left%n", turnsLeft);

            if (gameState.equals(GameState.WON)) {
                goblin.shape = "  ";
            } else if (gameState.equals(GameState.LOST)) {
                human.shape = "  ";
            }

            land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
            System.out.println(land);

            Main.printEndGameMessage(gameState);
        }
    }

    @FXML
    public void initialize() {
        mainGame();
    }
}