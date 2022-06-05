package genspark.humansvsgoblins.pieces;

import genspark.humansvsgoblins.land.Coordinates;

import java.util.Properties;
import java.util.Random;

/**
 * Goblin player
 */
public class Goblin extends Player {
    final Random random;

    /**
     * Create a goblin player at the given coordinates.
     *
     * @param coordinates Starting coordinates of Goblin
     */
    public Goblin(Coordinates coordinates) {
        super(coordinates);
        this.shape = "\uD83D\uDC7A"; //👺
        this.defaultShape = "\uD83D\uDC7A"; //👺
        this.random = new Random();
    }

    /**
     * Create a goblin player at the given coordinates with stats from the given properties.
     *
     * @param coordinates Starting coordinates of Goblin
     * @param properties  Initial game properties to get initial health and attack values
     */
    public Goblin(Coordinates coordinates, Properties properties) {
        this(coordinates);
        this.health = Integer.parseInt((String) properties.get("initialGoblinHealth"));
        this.attack = Integer.parseInt((String) properties.get("initialGoblinAttack"));
    }

    /**
     * Performs combat with the human player.
     *
     * @param human      Human player
     * @param randomness The amount of randomness in attack values. The higher the number the more
     *                   variation between player attack and actual attack inflicted. 0 means no randomness.
     * @return the human player with health affected by combat.
     */
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

    /**
     * Moves the goblin to pursue the human player.
     *
     * @param human          The human player.
     * @param turnsRemaining The maximum number of turns until the game ends.
     */
    public void move(Human human, int turnsRemaining) {
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
        } while (Math.max(Math.abs(xDiff), Math.abs(yDiff)) > Math.max(turnsRemaining, 2));
    }
}
