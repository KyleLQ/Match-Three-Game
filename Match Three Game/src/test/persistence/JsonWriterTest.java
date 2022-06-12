package persistence;

// class is based on JsonWriterTest from JsonSerializationDemo,
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Board;
import model.Player;
import model.UtilityTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends UtilityTest {

    @BeforeEach
    public void setup() {
        setupManyMatchGrid();
    }

    @Test
    public void testJsonWriterNoFileFound() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/\0lol");
            jsonWriter.open();
            fail("Expected FileNotFoundException, but didn't throw any exception at all!");
        } catch (FileNotFoundException fnfe) {
            // expected result
        }
    }

    @Test
    public void testJsonWriterEmptyBoardZeroPoints() {
        Board board = new Board(0);
        Player player = new Player();
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/testJsonWriterEmptyBoardZeroPoints.json");
            jsonWriter.open();
            jsonWriter.write(board,player);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testJsonWriterEmptyBoardZeroPoints.json");
            GameState gameState = jsonReader.read();
            Board actualBoard = gameState.getBoard();

            assertEquals(0, actualBoard.getRows());
            assertEquals(0,player.getPoints());
        } catch(IOException ioe) {
            fail("Caught IOException, but was not expecting an exception!");
        }
    }

    @Test
    public void testJsonWriterNormalBoardNormalPoints() {
        Board board = new Board(manyMatchGrid);
        Player player = new Player();
        player.setPoints(50);
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/testJsonWriterNormalBoardNormalPoints.json");
            jsonWriter.open();
            jsonWriter.write(board,player);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testJsonWriterNormalBoardNormalPoints.json");
            GameState gameState = jsonReader.read();
            Board actualBoard = gameState.getBoard();

            checkGridsSame(manyMatchGrid, actualBoard.getGrid());
            assertEquals(50,player.getPoints());
        } catch(IOException ioe) {
            fail("Caught IOException, but was not expecting an exception!");
        }
    }
}
