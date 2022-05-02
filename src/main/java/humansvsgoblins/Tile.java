package humansvsgoblins;

public class Tile {

    Piece piece;

    public Tile() {
        piece = null;
    }

    public Tile(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        if (piece == null)
            return "  ";
        return piece.toString();
    }
}
