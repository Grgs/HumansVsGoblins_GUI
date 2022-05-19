package genspark.humansvsgoblins;

import java.util.ArrayList;

/**
 * Player represents playable and non-playable characters. Currently, that is Human and Goblin.
 */
public class Player extends Piece {
    /**
     * Loot collected by the player.
     */
    ArrayList<Loot> inventory;

    /**
     * @param coordinates Initial coordinates of the player on the land.
     */
    public Player(Coordinates coordinates) {
        super(coordinates);
        inventory = new ArrayList<>();
    }

    /**
     * Create a player in the top left corner of the land.
     */
    public Player() {
        super(new Coordinates(0, 0));
        inventory = new ArrayList<>();
    }

    public ArrayList<Loot> getInventory() {
        return inventory;
    }

}
