package genspark.humansvsgoblins;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Land {
    final int maxColumns;
    final int maxRows;
    public ArrayList<ArrayList<Tile>> grid;

    public Land() {
        this.maxColumns = MaxCoordinates.maxCols;
        this.maxRows = MaxCoordinates.maxRows;
        this.grid = emptyGrid();
    }

    /**
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
     * @param coordinates coordinates of the piece being added to the grid
     * @param piece       The piece being added to the grid
     */
    public void setGrid(@NotNull Coordinates coordinates, Piece piece) {
        grid.get(coordinates.y).get(coordinates.x).setTile(piece);
    }

    /**
     * @param piece The piece being added to the grid.
     */
    public void setGrid(Piece piece) {
        setGrid(piece.getCoordinates(), piece);
    }

    /**
     * @param coordinates The coordinates where a new piece will be placed.
     */
    public void setGrid(@NotNull Coordinates coordinates) {
        grid.get(coordinates.y).get(coordinates.x).setTile(new Piece());
    }

    public Tile getGrid(@NotNull Coordinates coordinates) {
        return grid.get(coordinates.y).get(coordinates.x);
    }

    public void addPieces(@NotNull ArrayList<Piece> pieces) {
        for (Piece p : pieces) this.setGrid(p);
    }

    public void setInitialLand(GridPane gridPane) {
        for (int i = 0; i < MaxCoordinates.maxCols; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(20));
            for (int j = 0; j < MaxCoordinates.maxRows; j++) {
                gridPane.add(this.getGrid(new Coordinates(i, j)).label, i, j);
            }
        }
    }

    /**
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

    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b);
    }
}
