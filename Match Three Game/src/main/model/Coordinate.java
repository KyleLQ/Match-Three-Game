package model;

// represents the coordinates on a board; just simple container for row and col
public class Coordinate {
    private int row;
    private int col;

    // EFFECTS: constructs a coordinate with given row and col position
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
