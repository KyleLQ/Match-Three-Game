package persistence;

import model.Board;
import model.Gem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// class is based on JsonReader from JsonSerializationDemo,
// in particular the constructor, read(), and readFile() methods are taken directly from there.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// represents a reader that reads board and points data from Json file
public class JsonReader {
    private String source;

    // EFFECTS: makes a JsonReader that reads from source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the state of game from file and returns it
    //          throws IOException if there was an error reading from the file
    public GameState read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameState(jsonObject);
    }

    // EFFECTS: reads and returns source file as a String
    //          throws IOException if there was a problem reading the data from file
    public String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s));
        }

        return stringBuilder.toString();
    }

    // EFFECTS: parses and returns the game state from jsonObject
    private GameState parseGameState(JSONObject jsonObject) {
        int points = jsonObject.getInt("points");
        return new GameState(addBoard(jsonObject), points);
    }

    // REQUIRES: the board stored in the jsonObject is a square (row = col)
    // EFFECTS: parses and returns a board from jsonObject
    private Board addBoard(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("board");
        Gem[][] grid = new Gem[jsonArray.length()][jsonArray.length()];

        for (int row = 0; row < jsonArray.length(); row++) {
            JSONArray jsonArrayRow = jsonArray.getJSONArray(row);
            addRow(grid[row], jsonArrayRow);
        }

        return new Board(grid);
    }

    // MODIFIES: row
    // EFFECTS: parses gems from jsonObject and adds it to row
    private void addRow(Gem[] row, JSONArray jsonArray) {
        for (int col = 0; col < jsonArray.length(); col++) {
            int gemType = jsonArray.getInt(col);
            row[col] = new Gem(gemType);
        }
    }

    /*
    json array from "board"
    for each object in jsonarray:
    cast it to JsonArray
    send it to an addRow method

    addRow method
    for each object in JSonArray of row:
    get int associated with it, send it to gem. Add gem to the row of hte board.
     */

}
