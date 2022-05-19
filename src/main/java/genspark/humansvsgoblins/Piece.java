package genspark.humansvsgoblins;

/**
 * Piece represents a piece that can be added to Land. This can be playable or non-playable
 * characters, or pieces of loot.
 */
public class Piece {
    /**
     * The amount of attack dealt by the player.
     */
    public int attack;
    /**
     * The amount of health that the player has.
     */
    public int health;
    /**
     * A defence threshold that must be exceeded before the player gets damaged.
     */
    public int defence;
    /**
     * A string representing the player on the land. Can change based on the state of the player.
     */
    public String shape;
    /**
     * A string representing the player in a neutral phase on the land.
     */
    public String defaultShape;
    /**
     * Coordinates of the player describing where the player is on the land.
     */
    Coordinates coordinates;


    public Piece(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.shape = " ";
        this.defaultShape = " ";
    }

    /**
     * Create a piece in the top left corner of the land.
     */
    public Piece() {
        this(new Coordinates(0, 0));
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinates(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }

    public void moveNorth() {
        coordinates.setXY(coordinates.x, coordinates.y - 1);
    }

    public void moveSouth() {
        coordinates.setXY(coordinates.x, coordinates.y + 1);
    }

    public void moveEast() {
        coordinates.setXY(coordinates.x + 1, coordinates.y);
    }

    public void moveWest() {
        coordinates.setXY(coordinates.x - 1, coordinates.y);
    }

    @Override
    public String toString() {
        return this.shape;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

}
