package genspark.humansvsgoblins;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Tile {

    public Piece piece = null;
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
