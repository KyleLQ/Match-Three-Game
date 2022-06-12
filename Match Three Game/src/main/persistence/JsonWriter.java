package persistence;

import model.Board;
import model.Player;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// class is based on JsonWriter from JsonSerializationDemo,
// in particular the constructor, open, close, and saveToFile methods are taken directly from there.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of board and points earned to file
public class JsonWriter {
    private static final int INDENT = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer, writes to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    // IF FILE DOESN'T ALREADY EXIST, THEN PRINTWRITER WILL JUST CREATE A NEW FILE!!!

    // MODIFIES: this
    // EFFECTS: opens writer;
    //          throws FileNotFoundException if file cannot be written to
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of board and points to file
    public void write(Board board, Player player) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("board", board.toJsonArray());
        jsonObject.put("points", player.getPoints());
        saveToFile(jsonObject.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
