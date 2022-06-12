package persistence;

import model.Board;
import model.Gem;
import model.UtilityTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// class is based on JsonReaderTest from JsonSerializationDemo
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends UtilityTest {

    @Test
    public void testFileNotFound() {
        JsonReader jsonReader = new JsonReader("./data/meaningOfLife.json");
        try {
            jsonReader.read();
            fail("was expecting an IOException, but no exception thrown!");
        } catch (IOException ioe) {
            // expected result
        }
    }

    @Test
    public void testEmptyBoardZeroPoints() {
        JsonReader jsonReader = new JsonReader("./data/testJsonReaderEmptyBoardZeroPoints.json");

        GameState gameState = new GameState(new Board(0), 0);
        try {
            gameState = jsonReader.read();
        } catch (IOException ioe) {
            fail("Was not expecting any exception, but caught an IOException!");
        }
        Board actualBoard = gameState.getBoard();
        assertEquals(0, actualBoard.getRows());
        assertEquals(0, gameState.getPoints());
    }

    @Test
    public void testNormalBoardNormalPoints() {
        JsonReader jsonReader = new JsonReader("./data/testJsonReaderNormalBoardNormalPoints.json");

        GameState gameState = new GameState(new Board(0), 0);
        try {
            gameState = jsonReader.read();
        } catch (IOException ioe) {
            fail("Was not expecting any exception, but caught an IOException!");
        }
        assertEquals(20, gameState.getPoints());
        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.EMERALD), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.EMERALD), new Gem(Gem.DIAMOND),
                        new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.EMERALD), new Gem(Gem.ALUMINIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.EMERALD), new Gem(Gem.CHROMIUM)}};
        checkGridsSame(expectedGrid,gameState.getBoard().getGrid());
    }
}
