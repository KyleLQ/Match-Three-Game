package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest extends UtilityTest {
    Board board;

    @BeforeEach
    public void setup() {
        setupNoMatchThreeGrid();
        setupOneMatchGrid();
        setupManyMatchGrid();
        setupSmallGrid();
        setupEmptyAtTopGrid();
        setupEmptyAtDiagonalGrid();
        setupEmptyAtManyPlacesGrid();

        board = new Board(noMatchThreeGrid);
    }

    @Test
    public void testConstructorWithIntParam() {
        Board randomBoard = new Board(7);
        Gem[][] grid = randomBoard.getGrid();
        assertEquals(7, grid.length); //todo duplication
        for (Gem[] row : grid) {
            assertEquals(7, row.length);
            for (Gem gem : row) {
                assertNotNull(gem);
            }
        }
    }

    @Test
    public void testConstructorWith2DArrayParam() {
        checkGridsSame(noMatchThreeGrid, board.getGrid());
    }

    @Test
    public void testConstructorWith2DArrayParamNullSpaces() {
        board = new Board(emptyAtTopGrid);
        checkGridsSame(emptyAtTopGrid, board.getGrid());
    }

    @Test
    public void testCheckMatchThreeNoMatches() {
        List<Coordinate> coordinates = board.checkMatchThree();
        assertEquals(0, coordinates.size());
    }

    @Test
    public void testCheckMatchThreeOneMatch() {
        board.setGrid(oneMatchGrid);
        List<Coordinate> coordinates = board.checkMatchThree();

        // Arrays.asList creates fixed size !!!
        List<Coordinate> expectedList = Arrays.asList(new Coordinate(2, 4), new Coordinate(3, 4),
                new Coordinate(4, 4));
        checkListsSame(expectedList, coordinates);
    }

    @Test
    // NOTE THAT ORDER OF THE LIST DOESN'T MATTER, JUST THE CONTENTS!!!
    public void testCheckMatchThreeManyMatches() {
        board.setGrid(manyMatchGrid);
        List<Coordinate> coordinates = board.checkMatchThree();

        List<Coordinate> expectedList = Arrays.asList(new Coordinate(0, 2), new Coordinate(0, 3),
                new Coordinate(0, 4), new Coordinate(2, 0), new Coordinate(2, 1),
                new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(2, 2),
                new Coordinate(2, 3), new Coordinate(2, 2), new Coordinate(2, 3),
                new Coordinate(2, 4), new Coordinate(2, 2), new Coordinate(3, 3),
                new Coordinate(4, 4), new Coordinate(2, 4), new Coordinate(3, 4),
                new Coordinate(4, 4));

        checkListsSame(expectedList, coordinates);
    }

    @Test
    public void testCheckThreeSmallGrid() {
        board.setGrid(smallGrid);
        List<Coordinate> coordinates = board.checkMatchThree();

        List<Coordinate> expectedList = Arrays.asList(new Coordinate(0, 2),
                new Coordinate(1, 1), new Coordinate(2, 0));
        checkListsSame(expectedList, coordinates);
    }

    @Test
    public void testRemoveGemsEmptyList() {
        List<Coordinate> coordinates = new ArrayList<>();
        board.setGrid(noMatchThreeGrid);
        board.removeGems(coordinates);

        assertEquals(0, coordinates.size());

        Gem[][] expectedGrid = new Gem[][]{
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
        checkGridsSame(expectedGrid, board.getGrid());
    }


    @Test
    public void testRemoveGemsSmallListNoDuplicates() {
        board.setGrid(oneMatchGrid);
        List<Coordinate> coordinates = board.checkMatchThree();
        board.removeGems(coordinates);

        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.FELDSPAR), null},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.DIAMOND), null},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), null}};

        List<Coordinate> expectedList = Arrays.asList(new Coordinate(2, 4), new Coordinate(3, 4),
                new Coordinate(4, 4));

        checkListsSame(expectedList, coordinates);
        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testRemoveGemsLargeListYesDuplicates() {
        board.setGrid(manyMatchGrid);
        List<Coordinate> coordinates = board.checkMatchThree();
        board.removeGems(coordinates);

        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), null,
                        null, null},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.EMERALD),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {null, null, null, null, null},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        null, null},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.CHROMIUM), null}};

        List<Coordinate> expectedList = Arrays.asList(new Coordinate(0, 2), new Coordinate(0, 3),
                new Coordinate(0, 4), new Coordinate(2, 0), new Coordinate(2, 1),
                new Coordinate(2, 2), new Coordinate(2, 3), new Coordinate(2, 4),
                new Coordinate(3, 3), new Coordinate(4, 4), new Coordinate(3, 4));

        checkListsSame(expectedList, coordinates);
        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testMakeGemsFallDownNoEmptySpaces() {
        board.setGrid(noMatchThreeGrid);
        board.makeGemsFallDown();
        Gem[][] expectedGrid = new Gem[][]{
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

        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testMakeGemsFallDownEmptyAtTop() {
        board.setGrid(emptyAtTopGrid);
        board.makeGemsFallDown();
        Gem[][] expectedGrid = new Gem[][]{
                {null, null, null, null, null},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)}};

        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testMakeGemsFallDownEmptyAtDiagonal() {
        board.setGrid(emptyAtDiagonalGrid);
        board.makeGemsFallDown();
        Gem[][] expectedGrid = new Gem[][]{
                {null, null, null, null, null},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)}};
        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testMakeGemsFallDownEmptyAtManyPlaces() {
        board.setGrid(emptyAtManyPlacesGrid);
        board.makeGemsFallDown();
        Gem[][] expectedGrid = new Gem[][]{
                {null, null, null, null, new Gem(Gem.EMERALD)},
                {null, null, null,
                        null, new Gem(Gem.EMERALD)},
                {null, new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        null, new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD)}};
        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testAddNewRandomGemsNoEmptySpaces() {
        board.setGrid(noMatchThreeGrid);
        board.addNewRandomGems();

        Gem[][] expectedGrid = new Gem[][]{
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

        checkGridsSame(expectedGrid, board.getGrid());
    }

    @Test
    public void testAddNewRandomGemsManyEmptySpaces() {
        board.setGrid(emptyAtManyPlacesGrid);
        board.addNewRandomGems();

        assertEquals(5, board.getRows());
        for (Gem[] row : board.getGrid()) {
            assertEquals(5, row.length);
            for (Gem gem : row) {
                assertTrue(gem != null);
            }
        }
    }

    @Test
    public void testIsOnBoardNo() {

        int normalRow = (int) (board.getRows() / 2);
        int normalCol = (int) (board.getCols() / 2);
        int negRow = -1;
        int negCol = -2;
        int tooLargeRow = board.getRows();
        int tooLargeCol = board.getCols() + 3;

        assertFalse(board.isOnBoard(new Coordinate(tooLargeRow, tooLargeCol)));
        assertFalse(board.isOnBoard(new Coordinate(negRow, negCol)));
        assertFalse(board.isOnBoard(new Coordinate(tooLargeRow, negCol)));
        assertFalse(board.isOnBoard(new Coordinate(negRow, tooLargeCol)));
        assertFalse(board.isOnBoard(new Coordinate(normalRow, tooLargeCol)));
        assertFalse(board.isOnBoard(new Coordinate(tooLargeRow, normalCol)));
        assertFalse(board.isOnBoard(new Coordinate(normalRow, negCol)));
        assertFalse(board.isOnBoard(new Coordinate(negRow, normalCol)));
    }

    @Test
    public void testIsOnBoardYes() {
        assertTrue(board.isOnBoard(new Coordinate(2, 3)));
        assertTrue(board.isOnBoard(new Coordinate(0, 0)));
        assertTrue(board.isOnBoard(new Coordinate(4, 4)));
    }

    @Test
    public void testIsAdjacentNo() {
        assertFalse(board.isAdjacent(new Coordinate(0, 0), new Coordinate(4, 4)));
        assertFalse(board.isAdjacent(new Coordinate(0, 0), new Coordinate(0, 0)));
        assertFalse(board.isAdjacent(new Coordinate(2, 2), new Coordinate(3, 3)));
        assertFalse(board.isAdjacent(new Coordinate(2, 2), new Coordinate(1, 1)));
        // need to include the last test for full branch coverage !!!
    }

    @Test
    public void testIsAdjacentYes() {
        assertTrue(board.isAdjacent(new Coordinate(2, 2), new Coordinate(2, 3)));
        assertTrue(board.isAdjacent(new Coordinate(2, 2), new Coordinate(2, 1)));
        assertTrue(board.isAdjacent(new Coordinate(2, 2), new Coordinate(1, 2)));
        assertTrue(board.isAdjacent(new Coordinate(2, 2), new Coordinate(3, 2)));
    }

    @Test
    public void testHasEmptySpacesYes() {
        board.setGrid(emptyAtTopGrid);
        assertTrue(board.hasEmptySpaces());
    }

    @Test
    public void testHasEmptySpacesNo() {
        board.setGrid(manyMatchGrid);
        assertFalse(board.hasEmptySpaces());
    }

    @Test
    public void testClearBoard() {
        board.clearBoard();
        for (Gem[] row : board.getGrid()) {
            for (Gem gem : row) {
                assertNull(gem);
            }
        }
    }

    @Test
    public void testSwapGemsAdjacent() {
        Board actualBoard = board.swapGems(new Coordinate(3, 2), new Coordinate(3, 1));

        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.FELDSPAR), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD), new Gem(Gem.DIAMOND),
                        new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)}};

        checkGridsSame(expectedGrid, actualBoard.getGrid());
    }

    @Test
    public void testSwapGemsNotAdjacent() {
        Board actualBoard = board.swapGems(new Coordinate(4, 4), new Coordinate(1, 1));
        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.FELDSPAR), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE)}};

        checkGridsSame(expectedGrid, actualBoard.getGrid());
    }

    @Test
    public void testSetGrid() {
        board.setGrid(manyMatchGrid);
        Gem[][] expectedGrid = new Gem[][]{
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
        checkGridsSame(expectedGrid, board.getGrid());
    }
}