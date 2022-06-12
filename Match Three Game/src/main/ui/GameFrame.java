package ui;

import model.Board;
import model.Coordinate;
import model.Gem;
import model.Player;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// represents the game screen of the match three game. Users can make a swap, view points, use special abilities,
// or save their game.
public class GameFrame extends GeneralFrame {
    public static final int POINTS_PER_GEM = 1;

    // represents the effect that pressing the "next" button will have
    private enum MatchThreeAction {
        REMOVE_GEMS, FALL_DOWN_GEMS, ADD_NEW_GEMS
    }

    private Player player;
    private Board board;
    private Board boardToDraw; //this board reflects swaps that may not be permanent

    private JsonWriter jsonWriter;

    private JLabel pointsLabel;
    private JButton swapButton;
    private JButton abilityButton;
    private JButton saveButton;
    private JButton nextButton;
    private GameBoardScreen gameBoardScreen;

    // EFFECTS: initializes and makes visible the game screen and board
    public GameFrame(Player player, Board board) {
        super("Match Three Game");

        this.player = player;
        this.board = board;
        this.boardToDraw = board;
        jsonWriter = new JsonWriter(MainGUI.SAVE_FILE_LOCATION);
        gameBoardScreen = new GameBoardScreen();
        pointsLabel = new JLabel("Points: " + player.getPoints());
        pointsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        this.add(pointsLabel, BorderLayout.PAGE_START);
        this.add(gameBoardScreen, BorderLayout.CENTER);
        this.add(initializeButtons(), BorderLayout.PAGE_END);

        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons, and returns a JPanel containing a row of them
    @Override
    protected JPanel initializeButtons() {
        JPanel bottomButtons = super.initializeButtons();
        bottomButtons.add(initializeSwapButton());
        bottomButtons.add(initializeAbilityButton());
        bottomButtons.add(initializeSaveButton());
        bottomButtons.add(initializeNextButton());
        enableNextDisableOtherButtons();

        return bottomButtons;
    }

    // MODIFIES: this
    // EFFECTS: initializes the swap button
    private JButton initializeSwapButton() {
        swapButton = initializeButton("Make Swap", 4, new SwapHandler());
        return swapButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes the ability button
    private JButton initializeAbilityButton() {
        abilityButton = initializeButton("Clear Row", 4, new AbilityHandler());
        return abilityButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes the save button
    private JButton initializeSaveButton() {
        saveButton = initializeButton("Save Game", 4, new SaveHandler());
        return saveButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes the next button
    private JButton initializeNextButton() {
        nextButton = initializeButton("Next", 4, new NextHandler(MatchThreeAction.REMOVE_GEMS));
        return nextButton;
    }

    // MODIFIES: this
    // EFFECTS: disables every button
    private void disableAllButtons() {
        swapButton.setEnabled(false);
        abilityButton.setEnabled(false);
        saveButton.setEnabled(false);
        nextButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: enables next, disables every other button
    private void enableNextDisableOtherButtons() {
        swapButton.setEnabled(false);
        abilityButton.setEnabled(false);
        saveButton.setEnabled(false);
        nextButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: disables next, enables every other button
    private void disableNextEnableOtherButtons() {
        swapButton.setEnabled(true);
        abilityButton.setEnabled(true);
        saveButton.setEnabled(true);
        nextButton.setEnabled(false);
    }

    // JPanel that draws the board
    private class GameBoardScreen extends BoardScreen {

        // EFFECTS: initializes field as boardToDraw
        public GameBoardScreen() {
            super(boardToDraw);
        }

        // MODIFIES: g, this, GameFrame.this
        // EFFECTS: draws the boardToDraw and updates the points label
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            pointsLabel.setText("Points: " + player.getPoints());
        }
    }


    // handler when "save game" button is pressed
    private class SaveHandler implements ActionListener {

        // this actionPerformed method is based on the saveWorkRoom method from JsonSerializationDemo
        // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

        // MODIFIES: GameFrame.this
        // EFFECTS: saves the state of the game (board and points) to file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(board, player);
                jsonWriter.close();
                JOptionPane.showMessageDialog(GameFrame.this,
                        "Saved game to " + MainGUI.SAVE_FILE_LOCATION + " successfully!");
            } catch (FileNotFoundException fnfe) {
                JOptionPane.showMessageDialog(GameFrame.this,
                        "Unable to save game to " + MainGUI.SAVE_FILE_LOCATION + ". Try again!");
            }
        }
    }

    // handler when "swap button" is pressed
    private class SwapHandler implements ActionListener {

        // MODIFIES: GameFrame.this
        // EFFECTS: allows user to select two gems to swap
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(GameFrame.this, "Now click two adjacent gems to swap!");
            disableAllButtons();
            gameBoardScreen.addMouseListener(new SwapMouseListener());
        }
    }

    // Mouse Listener when "Swap" button is pressed
    private class SwapMouseListener extends MouseAdapter {
        private List<Coordinate> coordinates;

        // EFFECTS: constructs new listener with empty coordinates array
        public SwapMouseListener() {
            coordinates = new ArrayList<>();
        }

        // MODIFIES: GameFrame.this, this
        // EFFECTS: allows user to press mouse two times to select two gems
        @Override
        public void mousePressed(MouseEvent e) {
            if (coordinates.size() < 2) {
                int tileWidth = gameBoardScreen.getWidth() / board.getCols();
                int tileHeight = gameBoardScreen.getHeight() / board.getRows();

                int col = e.getX() / tileWidth;
                int row = e.getY() / tileHeight;

                int constrainedCol = Math.min(Math.max(0, col), board.getCols() - 1);
                int constrainedRow = Math.min(Math.max(0, row), board.getRows() - 1);

                coordinates.add(new Coordinate(constrainedRow, constrainedCol));
            }
            if (coordinates.size() >= 2) {
                gameBoardScreen.removeMouseListener(this);

                if (board.isAdjacent(coordinates.get(0), coordinates.get(1))) {
                    enableNextDisableOtherButtons();

                    boardToDraw = board.swapGems(coordinates.get(0), coordinates.get(1));
                    gameBoardScreen.setBoard(boardToDraw);
                    gameBoardScreen.repaint();
                } else {
                    JOptionPane.showMessageDialog(GameFrame.this, "Two gems not adjacent! Click the swap button again");
                    disableNextEnableOtherButtons();
                }
            }
        }
    }

    //handler when "Clear Row" button is pressed
    private class AbilityHandler implements ActionListener {

        // MODIFIES: GameFrame.this
        // EFFECTS: allows user to click a row to swap
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(GameFrame.this, "Now click a row to clear!");
            disableAllButtons();
            gameBoardScreen.addMouseListener(new AbilityMouseListener());
        }
    }

    // Mouse Listener when "Clear row" button is pressed
    private class AbilityMouseListener extends MouseAdapter {

        // MODIFIES: GameFrame.this
        // EFFECTS: allows user to press a row of gems to clear it
        @Override
        public void mousePressed(MouseEvent e) {
            int tileHeight = gameBoardScreen.getHeight() / board.getRows();
            int row = e.getY() / tileHeight;
            int constrainedRow = Math.min(Math.max(0, row), board.getRows() - 1);

            Gem[][] gems = board.getGrid();
            if (!player.removeRow(gems, constrainedRow)) {
                JOptionPane.showMessageDialog(GameFrame.this,
                        "You do not have " + Player.PTS_FOR_REMOVE_ROW + " points!");
                disableNextEnableOtherButtons();
                gameBoardScreen.removeMouseListener(this);
            } else {
                gameBoardScreen.removeMouseListener(this);
                boardToDraw = board;
                board.makeGemsFallDown();
                gameBoardScreen.setBoard(boardToDraw);
                gameBoardScreen.repaint();
                TimerListener timerListener = new TimerListener();
                Timer timer = new Timer(1000, timerListener);
                timerListener.setTimer(timer);
                timer.start();
            }
        }
    }

    // Listener for timer that allows delay in clearing row
    private class TimerListener implements ActionListener {
        private Timer timer;

        public void setTimer(Timer timer) {
            this.timer = timer;
        }

        // MODIFIES: this, GameFrame.this
        // EFFECTS: adds gems and enables next button, then stops timer
        @Override
        public void actionPerformed(ActionEvent e) {
            board.addNewRandomGems();
            gameBoardScreen.repaint();
            enableNextDisableOtherButtons();
            timer.stop();
        }
    }


    // Handler when "Next" button is pressed
    private class NextHandler implements ActionListener {
        private MatchThreeAction currentAction;

        // EFFECTS: initializes fields
        public NextHandler(MatchThreeAction actionToDo) {
            currentAction = actionToDo;
        }

        // MODIFIES: this, GameFrame.this
        // EFFECTS: allows the user to advance the state of the boardToDraw after swapping two gems
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentAction == MatchThreeAction.REMOVE_GEMS) {
                List<Coordinate> coordinates = boardToDraw.checkMatchThree();
                if (coordinates.isEmpty()) {
                    stopMatchThree();
                } else {
                    boardToDraw.removeGems(coordinates);
                    player.setPoints(POINTS_PER_GEM * coordinates.size() + player.getPoints());
                    gameBoardScreen.repaint();
                    currentAction = MatchThreeAction.FALL_DOWN_GEMS;
                }
            } else if (currentAction == MatchThreeAction.FALL_DOWN_GEMS) {
                boardToDraw.makeGemsFallDown();
                gameBoardScreen.repaint();
                currentAction = MatchThreeAction.ADD_NEW_GEMS;
            } else {
                boardToDraw.addNewRandomGems();
                gameBoardScreen.repaint();
                currentAction = MatchThreeAction.REMOVE_GEMS;
                board = boardToDraw;
            }
        }

        // MODIFIES: GameFrame.this
        // EFFECTS: stops removing gems if no more matches of three
        private void stopMatchThree() {
            JOptionPane.showMessageDialog(GameFrame.this,
                    "There are no more matches of 3 or more on the board displayed!");
            boardToDraw = board;
            gameBoardScreen.setBoard(boardToDraw);
            gameBoardScreen.repaint();
            disableNextEnableOtherButtons();
        }
    }
}
