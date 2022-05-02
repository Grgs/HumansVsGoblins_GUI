package genspark.humansvsgoblins;

import java.util.ArrayList;

public class Player extends Piece {
    /*Player represents playable and non-playable characters. Currently, that is Human and Goblin.*/
    ArrayList<Loot> inventory;


    public Player(Coordinates coordinates) {
        super(coordinates);
        inventory = new ArrayList<>();
    }

    public Player() {
        super(new Coordinates(0, 0));
        inventory = new ArrayList<>();
    }


    public ArrayList<Loot> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Loot> inventory) {
        this.inventory = inventory;
    }

}
