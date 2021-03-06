package genspark.humansvsgoblins.land;

import genspark.humansvsgoblins.pieces.Piece;
import genspark.humansvsgoblins.pieces.Player;
import genspark.humansvsgoblins.pieces.Players;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Playing surface where player and loot are to be displayed. Game map.
 */
public class Land {
    /**
     * Number of columns in the game map.
     */
    final int maxColumns;
    /**
     * Number of rows in the game map.
     */
    final int maxRows;
    /**
     * A grid of cells in the game map.
     */
    public ArrayList<ArrayList<Tile>> grid;

    public Land() {
        this.maxColumns = MaxCoordinates.maxCols;
        this.maxRows = MaxCoordinates.maxRows;
        this.grid = emptyGrid();
    }

    /**
     * Create an empty grid of tiles. The size of the gird is determined by maxRows and maxCols.
     *
     * @return a grid with empty tiles
     */
    public ArrayList<ArrayList<Tile>> emptyGrid() {
        ArrayList<ArrayList<Tile>> newGrid = new ArrayList<>();
        for (int i = 0; i < this.maxRows; i++) {
            ArrayList<Tile> columnGrid = new ArrayList<>();
            for (int j = 0; j < this.maxColumns; j++) {
                columnGrid.add(new Tile());
            }
            newGrid.add(columnGrid);
        }
        return newGrid;
    }

    /**
     * Put a piece at the given coordinates.
     *
     * @param coordinates coordinates of the piece being added to the grid
     * @param piece       The piece being added to the grid
     */
    public void setGrid(@NotNull Coordinates coordinates, Piece piece) {
        grid.get(coordinates.y).get(coordinates.x).setTile(piece);
    }

    /**
     * Put the piece on land.
     *
     * @param piece The piece being added to the grid.
     */
    public void setGrid(Piece piece) {
        setGrid(piece.getCoordinates(), piece);
    }

    /**
     * Create an empty tile at the coordinates.
     *
     * @param coordinates The coordinates where a new piece will be placed.
     */
    public void setGrid(@NotNull Coordinates coordinates) {
        grid.get(coordinates.y).get(coordinates.x).setTile(new Piece());
    }

    /**
     * Get the tile at the coordinates of the piece.
     *
     * @param piece The piece with the coordinates of the tile.
     * @return tile
     */
    public Tile getGrid(Piece piece) {
        return getGrid(piece.getCoordinates());
    }

    /**
     * Get the tile at the coordinates of the piece.
     *
     * @param coordinates coordinates of the tile to be retrieved
     * @return tile
     */
    public Tile getGrid(@NotNull Coordinates coordinates) {
        return grid.get(coordinates.y).get(coordinates.x);
    }

    /**
     * Add pieces to the land.
     *
     * @param pieces Pieces to add to the land.
     */
    public void addPieces(@NotNull ArrayList<Piece> pieces) {
        for (Piece p : pieces) this.setGrid(p);
    }

    /**
     * Creates a GridPane with the size of the land.
     *
     * @param gridPane a grid pane to be populated with tiles
     */
    public void setInitialLand(GridPane gridPane) {
        for (int i = 0; i < MaxCoordinates.maxCols; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(20));
            for (int j = 0; j < MaxCoordinates.maxRows; j++) {
                gridPane.add(this.getGrid(new Coordinates(i, j)).label, i, j);
            }
        }
    }

    /**
     * Redraw the land with players and loot from the input lists.
     *
     * @param players  Players to be added to the land
     * @param lootList The list of loot to be added to the land
     */
    public void update(Players players, ArrayList<Piece> lootList) {
        update(new ArrayList<>(List.of(new Player[]{players.getHuman(), players.getGoblin()})), lootList);
    }

    /**
     * Redraw the land with players and loot from the input lists.
     *
     * @param players  Players to be added to the land
     * @param lootList The list of loot to be added to the land
     */
    public void update(ArrayList<Piece> players, ArrayList<Piece> lootList) {
        for (int i = 0; i < this.maxRows; i++) {
            for (int j = 0; j < this.maxColumns; j++) {
                this.setGrid(new Coordinates(j, i));
            }
        }
        addPieces(lootList);
        addPieces(players);
    }

    /**
     * Get a String representation of the land.
     *
     * @return String representation of the land.
     */
    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b);
    }
}
