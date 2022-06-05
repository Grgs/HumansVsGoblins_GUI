package genspark.humansvsgoblins.land;

import org.jetbrains.annotations.NotNull;

/**
 * Coordinates of land. Coordinates start from the top left corner then the x and y values increase
 * down and right. Coordinates outside the max values will not produce an error; they will wrap around.
 */
public class Coordinates {
    /**
     * Maximum number of rows or y values.
     */
    final public int maxY;
    /**
     * Maximum number of columns or x values.
     */
    final public int maxX;
    public int y, x;

    public Coordinates(int x, int y) {
        this.maxX = MaxCoordinates.maxCols;
        this.maxY = MaxCoordinates.maxRows;
        this.x = x;
        this.y = y;
    }

    public Coordinates(@NotNull Coordinates coordinates) {
        this.maxX = MaxCoordinates.maxCols;
        this.maxY = MaxCoordinates.maxRows;
        this.x = coordinates.x;
        this.y = coordinates.y;
    }

    /**
     * Set new x and y values.
     *
     * @param x new x value
     * @param y new y values
     */
    public void setXY(int x, int y) {
        this.x = x % this.maxX;
        this.y = y % this.maxY;
        if (this.x < 0) this.x += this.maxX;
        if (this.y < 0) this.y += this.maxY;
    }

    /**
     * Determines if two coordinates are within 1 row or column.
     *
     * @param coordinates The other coordinates that could collide with this coordinates
     * @return true if the coordinates collide.
     */
    public boolean collidesWith(@NotNull Coordinates coordinates) {
        return (Math.abs(this.x - coordinates.x) + Math.abs(this.y - coordinates.y) < 2) ||
                (Math.abs(this.x - coordinates.x) == 1 && Math.abs(this.y - coordinates.y) == 1);
    }

    /**
     * @param coordinates The other coordinates that could equal these coordinates.
     * @return true if coordinates are equal.
     */
    public boolean equals(@NotNull Coordinates coordinates) {
        return this.x == coordinates.x && this.y == coordinates.y;
    }
}
