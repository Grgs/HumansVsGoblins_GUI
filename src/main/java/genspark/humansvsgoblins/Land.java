package genspark.humansvsgoblins;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Land {
    public ArrayList<ArrayList<Tile>> grid;
    //    ArrayList<ArrayList<Label>> landNodes;
    int maxColumns, maxRows;

    public Land() {
        this.maxColumns = MaxCoordinates.maxCols;
        this.maxRows = MaxCoordinates.maxRows;
        this.grid = emptyGrid();
    }

    public ArrayList<ArrayList<Tile>> emptyGrid() {
        ArrayList<ArrayList<Tile>> newGrid = new ArrayList<>();
//        Tile[] emptyColumns = new Tile[this.maxColumns];
        for (int i = 0; i <= this.maxRows; i++) {
            ArrayList<Tile> columnGrid = new ArrayList<>();
            for (int j = 0; j <= this.maxColumns; j++) {
                columnGrid.add(new Tile());
            }
            newGrid.add(columnGrid);
        }
//        Arrays.fill(emptyColumns, new Tile());
//        for (int i = 0; i <= this.maxRows - 1; i++) {
//            newGrid.add(new ArrayList<>(List.of(emptyColumns)));
//        }
        return newGrid;
    }

    public void setGrid(Coordinates coordinates, Piece piece) {
//        grid.get(coordinates.y).set(coordinates.x, new Tile(piece));
//        grid.get(coordinates.y).get(coordinates.x).label.setText(piece.toString());
        grid.get(coordinates.y).get(coordinates.x).setTile(piece);
//        grid.get(coordinates.y).get(coordinates.x).label.setText();

    }

    public void setGrid(Piece piece) {
        setGrid(piece.getCoordinates(), piece);
    }

    public void setGrid(Coordinates coordinates, Tile tile) {
        grid.get(coordinates.y).set(coordinates.x, tile);
    }

    public void setGrid(Coordinates coordinates) {
        grid.get(coordinates.y).get(coordinates.x).setTile(new Piece());
    }

    public Tile getGrid(Coordinates coordinates) {
        return grid.get(coordinates.y).get(coordinates.x);
    }

    public void addPieces(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            this.setGrid(p);
        }
    }

    public void update(ArrayList<Piece> players, ArrayList<Piece> lootList) {
        for (int i = 0; i <= this.maxRows; i++) {
            for (int j = 0; j <= this.maxColumns; j++) {
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
