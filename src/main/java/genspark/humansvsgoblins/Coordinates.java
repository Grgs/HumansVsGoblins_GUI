package genspark.humansvsgoblins;

public class Coordinates {
    int maxY, maxX;
    int y, x;

    public Coordinates(int x, int y) {
        this.maxX = MaxCoordinates.maxCols;
        this.maxY = MaxCoordinates.maxRows;
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates coordinates) {
        this.maxX = MaxCoordinates.maxCols;
        this.maxY = MaxCoordinates.maxRows;
        this.x = coordinates.x;
        this.y = coordinates.y;
    }

    public void setXY(int x, int y) {

        this.x = x % this.maxX;
        this.y = y % this.maxY;
        if (this.x < 0) this.x += this.maxX;
        if (this.y < 0) this.y += this.maxY;
    }

    public boolean collidesWith(Coordinates coordinates) {
        return (Math.abs(this.x - coordinates.x) + Math.abs(this.y - coordinates.y) < 2) ||
                (Math.abs(this.x - coordinates.x) == 1 && Math.abs(this.y - coordinates.y) == 1);
    }

    public boolean equals(Coordinates coordinates) {
        return this.x == coordinates.x && this.y == coordinates.y;
    }
}
