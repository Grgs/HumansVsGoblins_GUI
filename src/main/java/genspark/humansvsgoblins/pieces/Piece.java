package genspark.humansvsgoblins.pieces;

import genspark.humansvsgoblins.land.Coordinates;

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
    public Coordinates coordinates;


    /**
     * @param coordinates initial coordinates of the player
     */
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

    /**
     * Get the coordinates of the piece.
     *
     * @return coordinates of the piece on land
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Set the coordinates of the player on land.
     *
     * @param coordinates coordinates of the piece being added to the grid
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Set the coordinates of the player on land.
     *
     * @param x x-coordinate of the piece
     * @param y y-coordinate of the piece
     */
    public void setCoordinates(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }

    /**
     * Move the piece up the screen.
     */
    public void moveNorth() {
        coordinates.setXY(coordinates.x, coordinates.y - 1);
    }

    /**
     * Move the piece down the screen.
     */
    public void moveSouth() {
        coordinates.setXY(coordinates.x, coordinates.y + 1);
    }

    /**
     * Move the piece left on the screen.
     */
    public void moveEast() {
        coordinates.setXY(coordinates.x + 1, coordinates.y);
    }

    /**
     * Move the piece right on the screen.
     */
    public void moveWest() {
        coordinates.setXY(coordinates.x - 1, coordinates.y);
    }

    /**
     * @return a string representation of the piece.
     */
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
