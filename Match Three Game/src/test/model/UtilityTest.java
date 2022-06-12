package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// contains useful utility methods for constructing grids, and testing grids and lists
public abstract class UtilityTest {
    public Gem[][] noMatchThreeGrid;
    public Gem[][] oneMatchGrid;
    public Gem[][] manyMatchGrid;
    public Gem[][] smallGrid;

    public Gem[][] emptyAtTopGrid;
    public Gem[][] emptyAtDiagonalGrid;
    public Gem[][] emptyAtManyPlacesGrid;

    public void checkGridsSame(Gem[][] expectedGrid, Gem[][] actualGrid) {
        assertEquals(expectedGrid.length, actualGrid.length);
        for (int row = 0; row < expectedGrid.length; row++) {
            for (int col = 0; col < expectedGrid[row].length; col++) {
                if (expectedGrid[row][col] == null) {
                    assertNull(actualGrid[row][col]);
                } else {
                    assertEquals(expectedGrid[row][col].getType(), actualGrid[row][col].getType());
                }
            }
            assertEquals(expectedGrid[row].length, actualGrid[row].length);
        }
    }


    public void checkListsSame(List<Coordinate> expectedList, List<Coordinate> actualList) {
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getRow(), actualList.get(i).getRow());
            assertEquals(expectedList.get(i).getCol(), actualList.get(i).getCol());
        }
    }

    public void setupNoMatchThreeGrid() {
        noMatchThreeGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.FELDSPAR), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)}};
        /* noMatchThreeGrid:
        1 6 2 1 1
        2 2 1 2 6
        3 3 4 6 3
        4 4 5 4 4
        1 6 2 1 1
         */
    }

    public void setupOneMatchGrid() {
        oneMatchGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.FELDSPAR), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.DIAMOND), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.CHROMIUM)}};
        /* oneMatchGrid (2,4) (3,4) (4,4)
        1 6 2 1 1
        2 2 1 2 6
        3 3 4 6 3
        4 4 5 4 3
        1 6 2 1 3
         */
    }

    public void setupManyMatchGrid() {
        manyMatchGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.EMERALD),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.CHROMIUM), new Gem(Gem.ALUMINIUM)}};
        /* manyMatchGrid
        (0,2) (0,3) (0,4)
        (2,0) (2,1) (2,2) (2,3) (2,4)
        (3,3) (3,4)
        (4,4)
        1 6 1 1 1
        2 2 5 2 6
        1 1 1 1 1
        4 4 5 1 1
        1 6 2 3 1
         */
    }

    public void setupSmallGrid() {
        smallGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)}};

        /*
        smallGrid
        (0,2) (1,1) (2,0)
        1 2 3
        1 3 3
        3 1 1
         */
    }

    public void setupEmptyAtTopGrid() {
        emptyAtTopGrid = new Gem[][]{
                {null, null, null, null, null},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)}};
        /* emptyAtTopGrid (X= null/empty)
        X X X X X
        1 2 3 4 5
        1 2 3 4 5
        1 2 3 4 5
        1 2 3 4 5
         */
    }

    public void setupEmptyAtDiagonalGrid() {
        emptyAtDiagonalGrid = new Gem[][]{
                {null, new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), null, new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), null,
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        null, new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), null}};
        /* emptyAtDiagonalGrid (X= null/empty)
        X 2 3 4 5
        1 X 3 4 5
        1 2 X 4 5
        1 2 3 X 5
        1 2 3 4 X
         */
    }

    public void setupEmptyAtManyPlacesGrid() {
        emptyAtManyPlacesGrid = new Gem[][]{
                {null, new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), null, null,
                        null, new Gem(Gem.EMERALD)},
                {null, new Gem(Gem.BRONZE), null,
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        null, new Gem(Gem.EMERALD)},
                {null, null, new Gem(Gem.CHROMIUM),
                        null, new Gem(Gem.EMERALD)}};
        /* emptyAtManyPlacesGrid (X= null/empty)
        X 2 3 4 5
        1 X X X 5
        X 2 X 4 5
        1 2 3 X 5
        X X 3 X 5
         */
    }
}
