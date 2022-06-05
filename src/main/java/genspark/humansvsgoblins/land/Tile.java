package genspark.humansvsgoblins.land;

import genspark.humansvsgoblins.pieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A rectangular piece in the land.
 */
public class Tile {

    /**
     * A game piece that resides on the tile.
     */
    public Piece piece = null;
    /**
     * A graphical representation of what is on the tile.
     */
    public Label label;

    /**
     * Create a new tile.
     */
    public Tile() {
        label = new Label(" ");
        this.label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID,
                new CornerRadii(3.0), new BorderWidths(0.5))));
        this.label.setAlignment(Pos.CENTER);
    }

    /**
     * Create a new tile with a piece.
     *
     * @param piece piece to be placed on the tile
     */
    public Tile(Piece piece) {
        this();
        this.piece = piece;
        this.label.setText(piece.toString());
    }

    /**
     * Set the piece on the tile.
     *
     * @param piece The piece being added to the tile.
     */
    public void setTile(Piece piece) {
        this.piece = piece;
        this.label.setText(piece.toString());
    }

    /**
     * Get a string representation of the tile.
     *
     * @return a string representation of the tile.
     */
    @Override
    public String toString() {
        if (piece == null)
            return "  ";
        return piece.toString();
    }
}
