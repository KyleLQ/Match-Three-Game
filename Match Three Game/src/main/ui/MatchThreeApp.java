package ui;

import model.Board;
import model.Coordinate;
import model.Gem;
import model.Player;
import persistence.GameState;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// (I just assume that the user will enter only numbers here, as this is only temporary anyways.
//  - you would click buttons or gems to swap in the final program, not enter in numbers)

// Match Three Game Application
public class MatchThreeApp {
    public static final int POINTS_PER_GEM = 1;
    private static final String SAVE_FILE_LOCATION = "./data/savedGameState.json";

    private Board board;
    private Player player;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the Match Three game application
    public MatchThreeApp() {
        runMatchThreeApp();
    }

    // MODIFIES: this
    // EFFECTS: repeatedly swaps gems, uses special abilities, prints points, or exits game,
    //           based on user input
    private void runMatchThreeApp() {
        startNewGame();
        printBoard(board);
        doMatchThree(board);

        while (true) {
            System.out.println("Type \"1\" to swap two adjacent gems,"
                    + " or \"2\" to use a special ability to remove a row,"
                    + " or \"3\" to see your current points,\n" + " or \"4\" to save your game,"
                    + " or anything else to exit the game");
            int command = input.nextInt();
            // note: you type in 0 based numbers, (0,0) is top left corner
            if (command == 1) {
                doSwap();
            } else if (command == 2) {
                System.out.println("Type row to clear");
                doRemoveRowAbility(input.nextInt());
            } else if (command == 3) {
                System.out.println(player.getPoints() + " points");
            } else if (command == 4) {
                saveGameState();
            } else {
                System.out.println("exiting");
                System.exit(0);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: attempts to swap two gems on this board, coordinates of the two gems are specified by user
    private void doSwap() {
        System.out.println("type row of first gem to swap");
        int row1 = input.nextInt();
        System.out.println("type col of first gem to swap");
        int col1 = input.nextInt();

        System.out.println("type row of second gem to swap");
        int row2 = input.nextInt();
        System.out.println("type col of second gem to swap");
        int col2 = input.nextInt();

        Coordinate coordinate1 = new Coordinate(row1, col1);
        Coordinate coordinate2 = new Coordinate(row2, col2);

        if (board.isOnBoard(coordinate1) && board.isOnBoard(coordinate2)
                && board.isAdjacent(coordinate1, coordinate2)) {
            Board swappedBoard = board.swapGems(coordinate1, coordinate2);
            System.out.println("This is the board after your swap: \n");
            printBoard(swappedBoard);
            doMatchThree(swappedBoard);
        } else {
            System.out.println("coordinates are not adjacent or are out of bounds. try again!");
        }
    }

    // MODIFIES: selectedBoard, this
    // EFFECTS: repeatedly checks for matches of 3 or more on selectedBoard. If there are any matches, remove the
    //          gems involved in the matches and add new gems to the top of selectedBoard, and make this board be
    //          selectedBoard.
    private void doMatchThree(Board selectedBoard) {
        List<Coordinate> coordinates = selectedBoard.checkMatchThree();
        if (coordinates.isEmpty()) {
            System.out.println("there are no matches of 3 or more on this board!");
            return;
        }

        do {
            selectedBoard.removeGems(coordinates);
            printBoard(selectedBoard);

            int numPoints = POINTS_PER_GEM * coordinates.size();
            player.setPoints(player.getPoints() + numPoints);
            System.out.println("Current points: " + player.getPoints());

            selectedBoard.makeGemsFallDown();
            printBoard(selectedBoard);

            selectedBoard.addNewRandomGems();
            printBoard(selectedBoard);

            coordinates = selectedBoard.checkMatchThree();
        } while (!coordinates.isEmpty());
        board = selectedBoard; //unnecessary if not being called by doSwap
    }

    // MODIFIES: this
    // EFFECTS: removes a row from this board, then checks for any matches of 3 or more
    private void doRemoveRowAbility(int row) {
        if (!board.isOnBoard(new Coordinate(row, 0))) {
            System.out.println("row is not on board. Try Again");
            return;
        }

        Gem[][] gems = board.getGrid();
        if (!player.removeRow(gems, row)) {
            System.out.println("You do not have " + Player.PTS_FOR_REMOVE_ROW + " points!");
            return;
        }
        printBoard(board);

        board.makeGemsFallDown();
        printBoard(board);

        board.addNewRandomGems();
        printBoard(board);

        doMatchThree(board);
    }


    // MODIFIES: this
    // EFFECTS: initializes board, player, jsonReader, jsonWriter and scanner
    private void startNewGame() {
        input = new Scanner(System.in);
        player = new Player();
        jsonReader = new JsonReader(SAVE_FILE_LOCATION);
        jsonWriter = new JsonWriter(SAVE_FILE_LOCATION);

        System.out.println("Type \"1\" to generate a random board to play on,"
                + "\n or type \"2\" to generate a demo board, or \"3\" to load save state");
        int command = input.nextInt();
        if (command == 1) {
            board = new Board(8);
        } else if (command == 2) {
            board = new Board(setUpDemoBoard());
        } else if (command == 3) {
            loadGameState();
        } else {
            System.out.println("Command not recognized! exiting!");
            System.exit(1);
        }
    }

    // loadGameState method is based on the loadWorkRoom method from JsonSerializationDemo
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: loads the state of the game (board and points) from file
    private void loadGameState() {
        try {
            GameState gameState = jsonReader.read();
            board = gameState.getBoard();
            player.setPoints(gameState.getPoints()); //assume player already initialized at this point
            System.out.println("loaded save state successfully!");
        } catch (IOException ioe) {
            System.out.println("unable to read from file: " + SAVE_FILE_LOCATION);
            System.out.println("Try again: \n");
            startNewGame();
        }
    }

    // saveGameState method is based on the saveWorkRoom method from JsonSerializationDemo
    // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the state of the game (board and points) to file
    private void saveGameState() {
        try {
            jsonWriter.open();
            jsonWriter.write(board, player);
            jsonWriter.close();
            System.out.println("Saved game to " + SAVE_FILE_LOCATION + " successfully!");
        } catch (FileNotFoundException fnfe) {
            System.out.println("Unable to save game to " + SAVE_FILE_LOCATION + " successfully. Try again!");
        }
    }

    // EFFECTS: prints out the contents of selectedBoard
    private void printBoard(Board selectedBoard) {
        Gem[][] grid = selectedBoard.getGrid();
        for (Gem[] row : grid) {
            for (Gem gem : row) {
                if (gem != null) {
                    System.out.print(gem.getType() + " ");
                } else {
                    System.out.print("X" + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
    }

    // todo: I could instead just choose to load this from a file!
    // EFFECTS: initializes the grid to be used in the demo board
    private Gem[][] setUpDemoBoard() {
        Gem[][] demoGrid = {
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.CHROMIUM), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.EMERALD), new Gem(Gem.BRONZE), new Gem(Gem.DIAMOND), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.ALUMINIUM), new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.DIAMOND), new Gem(Gem.BRONZE), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.EMERALD), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.EMERALD), new Gem(Gem.FELDSPAR), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE), new Gem(Gem.DIAMOND), new Gem(Gem.CHROMIUM),
                        new Gem(Gem.DIAMOND), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.CHROMIUM), new Gem(Gem.BRONZE), new Gem(Gem.BRONZE),
                        new Gem(Gem.CHROMIUM), new Gem(Gem.ALUMINIUM), new Gem(Gem.EMERALD), new Gem(Gem.BRONZE)},
                {new Gem(Gem.EMERALD), new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.DIAMOND), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.FELDSPAR), new Gem(Gem.EMERALD), new Gem(Gem.DIAMOND), new Gem(Gem.FELDSPAR),
                        new Gem(Gem.BRONZE), new Gem(Gem.EMERALD), new Gem(Gem.FELDSPAR), new Gem(Gem.ALUMINIUM)}};

        return demoGrid;
    }
    /*
        Demo board:
        1 1 1 4 3 2 3 3
        5 2 4 1 4 4 5 6
        4 1 2 6 1 4 2 3
        3 6 5 1 2 5 6 4
        6 2 4 3 4 2 1 3
        4 3 2 2 3 1 5 2
        5 2 2 1 2 2 4 3
        6 5 4 6 2 5 6 1
         */


}
