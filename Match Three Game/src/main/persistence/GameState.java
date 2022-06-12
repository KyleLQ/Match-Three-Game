package persistence;

import model.Board;

// represents the state of a game, with the current board and points earned. Used for loading from file.
public class GameState {
    private Board board;
    private int points;

    // EFFECTS: constructs a new GameState object with fields given by parameter values
    public GameState(Board board, int points) {
        this.board = board;
        this.points = points;
    }

    public Board getBoard() {
        return board;
    }

    public int getPoints() {
        return points;
    }
}
