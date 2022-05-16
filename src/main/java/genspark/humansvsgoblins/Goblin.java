package genspark.humansvsgoblins;

import java.util.Properties;
import java.util.Random;

public class Goblin extends Player {
    final Random random;

    public Goblin(Coordinates coordinates) {
        super(coordinates);
        this.shape = "\uD83D\uDC7A"; //👺
        this.defaultShape = "\uD83D\uDC7A"; //👺
        this.random = new Random();
    }

    public Goblin(Coordinates coordinates, Properties properties) {
        this(coordinates);
        this.health = Integer.parseInt((String) properties.get("initialGoblinHealth"));
        this.attack = Integer.parseInt((String) properties.get("initialGoblinAttack"));
    }

    public Human combat(Human human, float randomness) {
        System.out.println("combat");
        int oldHumanHealth = human.getHealth();
        int oldGoblinHealth = this.getHealth();
        human.setHealth(oldHumanHealth + Math.min(-this.getAttack() -
                (int) (randomness * random.nextGaussian()) + human.getDefence(), 0));
        this.setHealth(oldGoblinHealth + Math.min(-human.getAttack() -
                (int) (randomness * random.nextGaussian()) + this.getDefence(), 0));
        System.out.printf("%s health has been reduced by %d%n%s health has been reduced by %d%n",
                human, oldHumanHealth - human.getHealth(), this, oldGoblinHealth - this.getHealth());
        return human;
    }

    public void move(Human human, int maxTurn) {
        int xDiff;
        int yDiff;
        do {
            xDiff = this.getCoordinates().x - human.getCoordinates().x;
            yDiff = this.getCoordinates().y - human.getCoordinates().y;
            if (Math.abs(xDiff) == Math.abs(yDiff)) {
                if (xDiff < 0) {
                    this.moveEast();
                } else {
                    this.moveWest();
                }
                if (yDiff < 0) {
                    this.moveSouth();
                } else {
                    this.moveNorth();
                }
            } else if (Math.abs(xDiff) > Math.abs(yDiff)) {
                if (xDiff < 0) {
                    this.moveEast();
                } else {
                    this.moveWest();
                }
            } else {
                if (yDiff < 0) {
                    this.moveSouth();
                } else {
                    this.moveNorth();
                }
            }
        } while (Math.max(Math.abs(xDiff), Math.abs(yDiff)) > Math.max(maxTurn, 2));
    }

    public void deStackPlayers(Human human) {
        if (human.getCoordinates().equals(this.getCoordinates()))
            moveEast();
    }
}
