package genspark.humansvsgoblins.pieces;

import genspark.humansvsgoblins.GameState;
import genspark.humansvsgoblins.land.Coordinates;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 * Goblin and human players.
 */
public class Players {
    /**
     * The human player.
     */
    public Human human;
    /**
     * The goblin player.
     */
    public Goblin goblin;

    /**
     * Set human and goblin players.
     *
     * @param human  The human player
     * @param goblin The goblin player
     */
    public Players(Human human, Goblin goblin) {
        this.human = human;
        this.goblin = goblin;
    }

    /**
     * If the goblin and human are on top of each other, then move them apart.
     */
    public void deStackPlayers() {
        if (human.getCoordinates().equals(goblin.getCoordinates()))
            goblin.moveEast();
    }

    /**
     * Removes whoever loses from the land.
     *
     * @param gameState Current state of the game
     */
    public void removeLosingPlayer(GameState gameState) {
        if (gameState.equals(GameState.WON)) {
            goblin.shape = "  ";
        } else if (gameState.equals(GameState.LOST)) {
            human.shape = "  ";
        }
    }

    /**
     * Initiates the battle between a human and a goblin.
     *
     * @param properties game properties from file
     * @param lootList   loot list on land
     */
    public void combat(Properties properties, ArrayList<Piece> lootList) {
        float randomness = Float.parseFloat((String) properties.get("combatRandomness"));
        Random random = new Random();
        int oldHumanHealth = human.getHealth();
        int oldGoblinHealth = goblin.getHealth();
        human.setHealth(oldHumanHealth + Math.min(-goblin.getAttack() -
                (int) (randomness * random.nextGaussian()) + human.getDefence(), 0));
        goblin.setHealth(oldGoblinHealth + Math.min(-human.getAttack() -
                (int) (randomness * random.nextGaussian()) + goblin.getDefence(), 0));
        System.out.printf("%s health has been reduced by %d%n%s health has been reduced by %d%n",
                human, oldHumanHealth - human.getHealth(), goblin, oldGoblinHealth - goblin.getHealth());
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

    /**
     * Get the human player.
     *
     * @return The human player
     */
    public Human getHuman() {
        return human;
    }

    /**
     * Get the goblin player.
     *
     * @return The goblin player
     */
    public Goblin getGoblin() {
        return goblin;
    }
}
