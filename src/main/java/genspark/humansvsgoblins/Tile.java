package genspark.humansvsgoblins;

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

    public Tile() {
        label = new Label(" ");
        this.label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID,
                new CornerRadii(3.0), new BorderWidths(0.5))));
        this.label.setAlignment(Pos.CENTER);
    }

    public Tile(Piece piece) {
        this();
        this.piece = piece;
        this.label.setText(piece.toString());
    }

    /**
     * @param piece The piece being added to the tile.
     */
    public void setTile(Piece piece) {
        this.piece = piece;
        this.label.setText(piece.toString());
    }

    @Override
    public String toString() {
        if (piece == null)
            return "  ";
        return piece.toString();
    }
}
