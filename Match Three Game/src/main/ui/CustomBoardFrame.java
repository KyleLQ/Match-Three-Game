package ui;

import model.Board;
import model.Coordinate;
import model.Gem;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// represents a screen where users can add gems to create a custom board
public class CustomBoardFrame extends GeneralFrame {
    private Board board;
    private Coordinate selectedTile;

    private JButton doneButton;
    private JButton resetButton;
    private CustomBoardScreen customBoardScreen;

    // EFFECTS initializes and makes visible the screen and board
    public CustomBoardFrame() {
        super("Make custom board!");

        initializeBoard();
        customBoardScreen = new CustomBoardScreen();
        customBoardScreen.addMouseListener(new SelectGemMouseListener());
        customBoardScreen.addKeyListener(new KeyHandler());

        this.add(customBoardScreen, BorderLayout.CENTER);
        this.add(initializeButtons(), BorderLayout.PAGE_END);
        this.setVisible(true);
        JOptionPane.showMessageDialog(this, "Click a tile, then type a number from 1-7 to add the gem there");
        customBoardScreen.grabFocus();
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons, and returns a JPanel containing a row of them
    @Override
    protected JPanel initializeButtons() {
        JPanel bottomButtons = super.initializeButtons();
        bottomButtons.add(initializeResetButton());
        bottomButtons.add(initializeDoneButton());

        return bottomButtons;
    }

    // MODIFIES: this
    // EFFECTS: initializes the reset button
    private JButton initializeResetButton() {
        resetButton = initializeButton("Reset Board", 2, e -> {
            initializeBoard();
            customBoardScreen.setBoard(board);
            customBoardScreen.repaint();
            customBoardScreen.grabFocus();
        });
        return resetButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes the done button
    private JButton initializeDoneButton() {
        doneButton = initializeButton("Done", 2, new DoneHandler());
        return doneButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes board field by setting everything to null, and moving selectedTile to top left corner
    private void initializeBoard() {
        board = new Board(8);
        board.clearBoard();
        selectedTile = new Coordinate(0, 0);
    }

    // JPanel that draws the board
    private class CustomBoardScreen extends BoardScreen {

        // EFFECTS: initializes field as board
        public CustomBoardScreen() {
            super(board);
        }

        // MODIFIES: g, this
        // EFFECTS: draws the board
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int tileWidth = getWidth() / board.getCols();
            int tileHeight = getHeight() / board.getRows();

            highlightSelectedGem((Graphics2D) g, tileWidth, tileHeight);
        }

        // MODIFIES: g,this
        // EFFECTS: draws a highlight on the selected gem
        public void highlightSelectedGem(Graphics2D g2, int tileWidth, int tileHeight) {
            g2.setStroke(new BasicStroke(10));
            g2.setColor(new Color(150, 150, 250));
            g2.drawRect(selectedTile.getCol() * tileWidth, selectedTile.getRow() * tileHeight, tileWidth, tileHeight);
        }
    }

    // handler when "Done" button is pressed
    private class DoneHandler implements ActionListener {

        // MODIFIES: CustomBoardFrame.this
        // EFFECTS: goes to the GameFrame, if no empty spaces on board
        @Override
        public void actionPerformed(ActionEvent e) {
            if (board.hasEmptySpaces()) {
                JOptionPane.showMessageDialog(CustomBoardFrame.this, "add gems to all the spaces first!");
                return;
            }
            CustomBoardFrame.this.setVisible(false);
            new GameFrame(new Player(), board);
            CustomBoardFrame.this.dispose();
        }
    }

    // Mouse Listener for selecting gems
    private class SelectGemMouseListener extends MouseAdapter {

        // MODIFIES: CustomBoardFrame.this
        // EFFECTS: allows user to click a tile to make that one selected, updates corresponding field
        @Override
        public void mousePressed(MouseEvent e) {
            int tileWidth = customBoardScreen.getWidth() / board.getCols();
            int tileHeight = customBoardScreen.getHeight() / board.getRows();

            int col = e.getX() / tileWidth;
            int row = e.getY() / tileHeight;

            int constrainedCol = Math.min(Math.max(0, col), board.getCols() - 1);
            int constrainedRow = Math.min(Math.max(0, row), board.getRows() - 1);

            selectedTile = new Coordinate(constrainedRow, constrainedCol);
            customBoardScreen.repaint();
            customBoardScreen.grabFocus();
        }
    }

    // Key handler to add a new gem to the selected tile
    private class KeyHandler extends KeyAdapter {

        // MODIFIES: CustomBoardFrame.this
        // EFFECTS: adds a new gem to the selected tile, based on the key pressed
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.ALUMINIUM));
                    break;
                case KeyEvent.VK_2: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.BRONZE));
                    break;
                case KeyEvent.VK_3: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.CHROMIUM));
                    break;
                case KeyEvent.VK_4: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.DIAMOND));
                    break;
                case KeyEvent.VK_5: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.EMERALD));
                    break;
                case KeyEvent.VK_6: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.FELDSPAR));
                    break;
                case KeyEvent.VK_7: board.setGem(selectedTile.getRow(), selectedTile.getCol(), new Gem(Gem.GOLD));
                    break;
                default: break;
            }
            customBoardScreen.repaint();
        }
    }
}
