package ui;

import model.Board;
import model.Player;
import persistence.GameState;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// represents the title screen of the Match Three game. Users can generate a random board,
// load a saved game, or make a custom board to play on.
public class TitleFrame extends GeneralFrame {
    public static final String TITLE_IMAGE_LOCATION = "./data/title.png";

    private JButton randomBoard;
    private JButton loadSaveState;
    private JButton customBoard;

    // EFFECTS: initializes and makes visible the title screen of the game.
    public TitleFrame() {
        super("Match Three Game");

        this.add(new TitleScreen(), BorderLayout.CENTER);
        this.add(initializeButtons(), BorderLayout.PAGE_END);

        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons, and returns a JPanel containing a row of them
    @Override
    protected JPanel initializeButtons() {
        JPanel bottomButtons = super.initializeButtons();
        bottomButtons.add(initializeRandomBoardButton());
        bottomButtons.add(initializeLoadButton());
        bottomButtons.add(initializeCustomBoardButton());
        return bottomButtons;
    }

    // MODIFIES: this
    // EFFECTS: initializes the make random board button
    private JButton initializeRandomBoardButton() {
        randomBoard = initializeButton("Random Board", 3, new RandomBoardHandler());
        return randomBoard;
    }

    // MODIFIES: this
    // EFFECTS: initializes the load save state button
    private JButton initializeLoadButton() {
        loadSaveState = initializeButton("Load Game", 3, new LoadSaveHandler());
        return loadSaveState;
    }

    // MODIFIES: this
    // EFFECTS: initializes the make custom board button
    private JButton initializeCustomBoardButton() {
        customBoard = initializeButton("Custom Board", 3, new CustomBoardHandler());
        return customBoard;
    }


    // JPanel that draws the title screen
    private class TitleScreen extends JPanel {

        // MODIFIES: g, this
        // EFFECTS: draws the title screen
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();    // 984
            int height = getHeight(); // 921

            try {
                g2.drawImage(ImageIO.read(new File(TITLE_IMAGE_LOCATION)), width / 10, height / 4, this);
            } catch (IOException e) {
                //
            }

            Font font = new Font("Comic Sans MS", Font.BOLD, 60);
            g2.setFont(font);
            g2.setColor(Color.PINK);
            g2.drawString("MATCH THREE GAME", width / 6, height / 6);
        }
    }


    // handler when random board button is clicked
    private class RandomBoardHandler implements ActionListener {

        // MODIFIES: TitleFrame.this
        // EFFECTS: constructs a new random board and player, and sends it to a new Game Frame
        @Override
        public void actionPerformed(ActionEvent e) {
            Player player = new Player();
            Board board = new Board(MainGUI.BOARD_SIDE_LENGTH);
            TitleFrame.this.setVisible(false);
            new GameFrame(player, board);
            TitleFrame.this.dispose();
        }
    }

    // handler when load save state button is clicked
    private class LoadSaveHandler implements ActionListener {

        // this actionPerformed method is based on the loadWorkRoom method from JsonSerializationDemo
        // link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

        // MODIFIES: TitleFrame.this
        // EFFECTS: loads the state of the game (board and points) from file, and sends them to new Game Frame
        @Override
        public void actionPerformed(ActionEvent e) {
            JsonReader jsonReader = new JsonReader(MainGUI.SAVE_FILE_LOCATION);

            try {
                GameState gameState = jsonReader.read();
                Board board = gameState.getBoard();
                Player player = new Player();
                player.setPoints(gameState.getPoints());
                TitleFrame.this.setVisible(false);
                new GameFrame(player, board);
                TitleFrame.this.dispose();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(TitleFrame.this, "Unable to read from file: "
                        + MainGUI.SAVE_FILE_LOCATION + ".\n Try again.");
            }
        }
    }

    // handler when custom board button clicked
    private class CustomBoardHandler implements ActionListener {

        // MODIFIES: TitleFrame.this
        // EFFECTS: constructs a new Custom Board Frame
        @Override
        public void actionPerformed(ActionEvent e) {
            TitleFrame.this.setVisible(false);
            new CustomBoardFrame();
            TitleFrame.this.dispose();
        }
    }
}
