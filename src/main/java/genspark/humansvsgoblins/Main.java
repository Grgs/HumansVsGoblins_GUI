package genspark.humansvsgoblins;

import java.io.FileReader;
import java.util.*;

public class Main {
    public static Properties getProperties() {
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

    public static void initializePlayers(Properties properties, Goblin goblin, Human human) {
        goblin.setCoordinates(0, 0);
        goblin.setHealth(Integer.parseInt((String) properties.get("initialGoblinHealth")));
        goblin.setAttack(Integer.parseInt((String) properties.get("initialGoblinAttack")));
        human.setCoordinates(MaxCoordinates.maxCols / 2, MaxCoordinates.maxRows / 2);
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

    public static String printEndGameMessage(GameState gameState) {
        switch (gameState) {
            case WON:
                return ("You won!");
            case LOST:
                return  ("You lost!");
            case DRAW:
                return ("You survived!");
        }
        return "";
    }

    public static void main(String[] args) {
        Properties properties = getProperties();
        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));
        int turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));

        Land land = new Land();
        Goblin goblin = new Goblin();
        Human human = new Human();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        initializePlayers(properties, goblin, human);

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
            gameState = determineGameState(turnsLeft, goblin, human, gameState);

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

            System.out.println(printEndGameMessage(gameState));
        }
    }

}
