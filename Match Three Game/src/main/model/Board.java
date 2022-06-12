package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// todo:
// duplication in board maybe, and also in test methods? - i.e. first constructor and add new random gems-
//          only difference is the boolean


// represents the board in a match three game, contains a grid of gems
public class Board {
    private Gem[][] grid;
    // ROW MAJOR ORDER: grid[2][3] refers to the space at row 2, col 3
    // ALSO: grid[0][0] refers to the space at the top left of the board
    // Use 0 based numbers.


    // REQUIRES: sideLength >= 0
    // EFFECTS: constructs a new square board with sideLength rows and cols;
    //          gems of random type are added to board
    public Board(int sideLength) {
        grid = new Gem[sideLength][sideLength];
        for (int row = 0; row < sideLength; row++) {
            for (int col = 0; col < sideLength; col++) {
                int randomType = (int) (Math.random() * Gem.GOLD) + 1;
                setGem(row, col, new Gem(randomType));
            }
        }
        EventLog.getInstance().logEvent(new Event("Finished new board w/ random gems, rows and cols: " + sideLength));
    }

    // (use this constructor for setting up demo boards)
    // REQUIRES: grid is a square (rows = cols)
    // EFFECTS: constructs a new board with grid given by the param
    public Board(Gem[][] grid) {
        this.grid = grid;
        for (int row = 0; row < grid.length; row++) { // do this to log since doesn't make sense to call setGem here
            for (int col = 0; col < grid[row].length; col++) {
                Gem gem = grid[row][col];
                String gemType = (gem == null) ? ", Removed gem" : ", added gem of type: " + gem.getType();
                EventLog.getInstance().logEvent(new Event("at row: " + row + ", col: " + col + gemType));
            }
        }
        EventLog.getInstance().logEvent(new Event("Created new board based on a given grid of gems"));
    }


    // REQUIRES: this board has no empty spaces (i.e. no null spaces)
    // EFFECTS: returns list containing coordinates of all gems that are part of a row of at least
    //          3 of the same type. (row= horizontal, diagonal, or vertical)
    //          (there may be duplicates in the list)
    public List<Coordinate> checkMatchThree() {
        List<Coordinate> coordinates = new ArrayList<>();

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                checkMatchThreeTwoSpacesRight(coordinates, row, col);
                checkMatchThreeTwoSpacesDown(coordinates, row, col);
                checkMatchThreeTwoSpacesDownLeftDiagonal(coordinates, row, col);
                checkMatchThreeTwoSpacesDownRightDiagonal(coordinates, row, col);
            }
        }

        return coordinates;
    }

    // REQUIRES: this board has no empty (null) spaces
    // MODIFIES: coordinates
    // EFFECT: if the gem at position (row,col) on this board has the same type as the two gems directly to the right
    //         of it, (and the 3 gems are not at an out of bounds position),
    //         then add all 3 of those gems to coordinates. Else do nothing.
    private void checkMatchThreeTwoSpacesRight(List<Coordinate> coordinates, int row, int col) {
        if (((col + 2) < getCols()) && (getGem(row, col).getType() == getGem(row, col + 1).getType())
                && (getGem(row, col).getType() == getGem(row, col + 2).getType())) {
            coordinates.add(new Coordinate(row, col));
            coordinates.add(new Coordinate(row, col + 1));
            coordinates.add(new Coordinate(row, col + 2));
        }
    }

    // REQUIRES: this board has no empty (null) spaces
    // MODIFIES: coordinates
    // EFFECT: if the gem at position (row,col) on this board has the same type as the two gems directly below
    //         it, (and the 3 gems are not at an out of bounds position),
    //         then add all 3 of those gems to coordinates. Else do nothing.
    private void checkMatchThreeTwoSpacesDown(List<Coordinate> coordinates, int row, int col) {
        if (((row + 2) < getRows()) && (getGem(row, col).getType() == getGem(row + 1, col).getType())
                && (getGem(row, col).getType() == getGem(row + 2, col).getType())) {
            coordinates.add(new Coordinate(row, col));
            coordinates.add(new Coordinate(row + 1, col));
            coordinates.add(new Coordinate(row + 2, col));
        }
    }

    // REQUIRES: this board has no empty (null) spaces
    // MODIFIES: coordinates
    // EFFECT: if the gem at position (row,col) on this board has the same type as the two gems to the down left
    //         diagonal of it, (and the 3 gems are not at an out of bounds position),
    //         then add all 3 of those gems to coordinates. Else do nothing.
    private void checkMatchThreeTwoSpacesDownLeftDiagonal(List<Coordinate> coordinates, int row, int col) {
        if (((row + 2) < getRows()) && ((col - 2) >= 0)
                && (getGem(row, col).getType() == getGem(row + 1, col - 1).getType())
                && (getGem(row, col).getType() == getGem(row + 2, col - 2).getType())) {
            coordinates.add(new Coordinate(row, col));
            coordinates.add(new Coordinate(row + 1, col - 1));
            coordinates.add(new Coordinate(row + 2, col - 2));
        }
    }

    // REQUIRES: this board has no empty (null) spaces
    // MODIFIES: coordinates
    // EFFECT: if the gem at position (row,col) on this board has the same type as the two gems to the down right
    //         diagonal of it, (and the 3 gems are not at an out of bounds position),
    //         then add all 3 of those gems to coordinates. Else do nothing.
    private void checkMatchThreeTwoSpacesDownRightDiagonal(List<Coordinate> coordinates, int row, int col) {
        if (((row + 2) < getRows()) && ((col + 2) < getCols())
                && (getGem(row, col).getType() == getGem(row + 1, col + 1).getType())
                && (getGem(row, col).getType() == getGem(row + 2, col + 2).getType())) {
            coordinates.add(new Coordinate(row, col));
            coordinates.add(new Coordinate(row + 1, col + 1));
            coordinates.add(new Coordinate(row + 2, col + 2));
        }
    }


    // REQUIRES: coordinates contain valid positions on this board, this board has no empty(null) spaces
    // MODIFIES: this, coordinates
    // EFFECTS: for all coordinates in given list of coordinates, make those corresponding spaces on this board null.
    //          Also remove all duplicate coordinates in coordinates list.
    public void removeGems(List<Coordinate> coordinates) {
        for (int index = 0; index < coordinates.size(); index++) {
            Coordinate coordinate = coordinates.get(index);
            if (getGem(coordinate.getRow(), coordinate.getCol()) != null) {
                setGem(coordinate.getRow(), coordinate.getCol(), null);
            } else {
                coordinates.remove(index);
                index--;
            }
        }
        EventLog.getInstance().logEvent(new Event("Finished removing gems that were part of matches"
                + " of three or more"));
    }

    // (if I need to animate each gem falling one space at a time, I can move while loop out of this method)
    // MODIFIES: this
    // EFFECTS: if there is an empty space(null) below a gem, make that gem fall down into that empty space
    public void makeGemsFallDown() {
        boolean goAgain = true;
        while (goAgain) {
            goAgain = false;

            for (int row = 1; row < getRows(); row++) { // if there were empty spaces at top, do nothing anyways
                for (int col = 0; col < getCols(); col++) {
                    if (getGem(row, col) == null && getGem(row - 1, col) != null) {
                        // only have to check the one above, since we check each row downwards
                        goAgain = true;
                        Gem gemAbove = getGem(row - 1, col);
                        setGem(row - 1, col, getGem(row, col));
                        setGem(row, col, gemAbove);
                    }
                }
            }
            EventLog.getInstance().logEvent(new Event("All gems that are able to, have fallen down one space"));
        }
    }

    // MODIFIES: this
    // EFFECTS: add new gems of random type into all empty spaces in this board
    public void addNewRandomGems() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                if (getGem(row, col) == null) {
                    int randomType = (int) (Gem.GOLD * Math.random()) + 1;
                    setGem(row, col, new Gem(randomType));
                }
            }
        }
        EventLog.getInstance().logEvent(new Event("Finished adding new random gems"));
    }


    // REQUIRES: this board is a rectangle (all rows have same amount of columns)
    // EFFECTS: returns true if the coordinate represents a position that exists on this board
    //          (the coordinate has row in [0, grid.length-1] and col in [0,grid[0].length-1])
    public boolean isOnBoard(Coordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();
        return (row >= 0) && (row < getRows()) && (col >= 0) && (col < getCols());
    }

    // REQUIRES: the coordinates have row in [0, grid.length-1] and col in [0,grid[0].length-1]
    // EFFECTS: returns true if the two coordinates represent positions adjacent to each other
    //          (NOT DIAGONALLY)
    public boolean isAdjacent(Coordinate coord1, Coordinate coord2) {
        boolean coord2RightOfCoord1 = (coord1.getRow() == coord2.getRow())
                && (coord1.getCol() == coord2.getCol() + 1);
        boolean coord2LeftOfCoord1 = (coord1.getRow() == coord2.getRow())
                && (coord1.getCol() == coord2.getCol() - 1);
        boolean coord2TopOfCoord1 = (coord1.getRow() == coord2.getRow() - 1)
                && (coord1.getCol() == coord2.getCol());
        boolean coord2BotOfCoord1 = (coord1.getRow() == coord2.getRow() + 1)
                && (coord1.getCol() == coord2.getCol());

        return coord2RightOfCoord1 || coord2LeftOfCoord1 || coord2TopOfCoord1 || coord2BotOfCoord1;
    }

    // EFFECTS: returns true if this board has any empty spaces, false otherwise
    public boolean hasEmptySpaces() {
        for (Gem[] row : grid) {
            for (Gem gem : row) {
                if (gem == null) {
                    return true;
                }
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes all gems from this board by setting everything to null
    public void clearBoard() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                setGem(row,col,null);
            }
        }
        EventLog.getInstance().logEvent(new Event("Cleared board of all gems"));
    }

    // REQUIRES: the coordinates have row in [0, grid.length-1] and col in [0,grid[0].length-1]
    // EFFECTS: returns a new board that is the result of swapping the gems on this board in coord1 and coord2
    public Board swapGems(Coordinate coord1, Coordinate coord2) {
        Board newBoard = copyBoard();
        Gem swappedGem = newBoard.getGem(coord1.getRow(), coord1.getCol());
        newBoard.setGem(coord1.getRow(), coord1.getCol(), newBoard.getGem(coord2.getRow(), coord2.getCol()));
        newBoard.setGem(coord2.getRow(), coord2.getCol(), swappedGem);
        EventLog.getInstance().logEvent(new Event("Finished swapping gems"));

        return newBoard;
    }

    // EFFECTS: makes and returns a copy of this board
    private Board copyBoard() {
        Gem[][] newGrid = new Gem[getRows()][getCols()];
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                int originalType = getGem(row, col).getType();
                newGrid[row][col] = new Gem(originalType);
            }
        }
        return new Board(newGrid);
    }

    public Gem[][] getGrid() {
        return grid;
    }

    public void setGrid(Gem[][] grid) {
        this.grid = grid;
        EventLog.getInstance().logEvent(new Event("Set board to have a new grid of gems"));
    }

    public int getRows() {
        return grid.length;
    }

    // REQUIRES: this board's grid is a rectangle (all rows have the same amount of columns)
    // EFFECTS: returns the number of columns that this board has
    public int getCols() {
        return grid[0].length;
    }

    // REQUIRES: row is in [0, grid.length - 1], col is in [0, grid[row].length - 1]
    // EFFECTS: returns the gem on the board at position (row,col)
    public Gem getGem(int row, int col) {
        return grid[row][col];
    }

    // REQUIRES: row is in [0, grid.length - 1], col is in [0, grid[row].length - 1]
    // MODIFIES: this
    // EFFECTS: sets the position on the board at (row,col) to have gem
    // NOTE: null is allowed value for gem
    public void setGem(int row, int col, Gem gem) {
        grid[row][col] = gem;
        String gemType = (gem == null) ? ", Removed gem" : ", added gem of type: " + gem.getType();
        EventLog.getInstance().logEvent(new Event("at row: " + row + ", col: " + col + gemType));
    }

    // this method is based on the thingiesToJson() method in WorkRoom class from JsonSerializationDemo
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: returns this board of gems as a JSON 2D array
    public JSONArray toJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Gem[] row : grid) {
            jsonArray.put(rowToJson(row));
        }
        return jsonArray;
    }

    // this method is based on the thingiesToJson() method in WorkRoom class from JsonSerializationDemo
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: returns a row of gems as a JSON array
    private JSONArray rowToJson(Gem[] row) {
        JSONArray jsonArray = new JSONArray();
        for (Gem gem : row) {
            jsonArray.put(gem.getType());
        }
        return jsonArray;
    }
}
