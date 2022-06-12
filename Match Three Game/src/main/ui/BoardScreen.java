package ui;

import model.Board;
import model.Gem;

import javax.swing.*;
import java.awt.*;

// JPanel that can draw a board of gems
public abstract class BoardScreen extends JPanel {
    private Board board; // ensure that this board field is updated to be the same as board field you want to draw

    // EFFECTS: initializes fields, calls super constructor
    public BoardScreen(Board board) {
        super();
        this.board = board;
    }

    // MODIFIES: g, this
    // EFFECTS: draws the board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tileWidth = getWidth() / board.getCols();
        int tileHeight = getHeight() / board.getRows();
        drawBoard(g2, tileWidth, tileHeight);
        drawGems(g2, tileWidth, tileHeight);
    }

    // MODIFIES: g2, this
    // EFFECTS: draws an empty board with nothing on it
    public void drawBoard(Graphics2D g2, int tileWidth, int tileHeight) {
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        for (int row = 0; row < board.getRows(); row++) {
            g2.drawLine(0, row * tileHeight, getWidth(), row * tileHeight);
        }
        for (int col = 0; col < board.getCols(); col++) {
            g2.drawLine(col * tileWidth, 0, col * tileWidth, getHeight());
        }
    }

    // MODIFIES: g, this
    // EFFECTS: draws gems
    public void drawGems(Graphics g, int tileWidth, int tileHeight) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                int gemType = (board.getGem(row, col) == null) ? 100 : board.getGem(row, col).getType();
                int x = col * tileWidth;
                int y = row * tileHeight;
                drawGem(g, x, y, tileWidth, tileHeight, gemType);
            }
        }
    }

    // MODIFIES: g, this
    // EFFECTS: draws the gem of type gemType at the given x and y, with the given width and height
    public void drawGem(Graphics g, int x, int y, int tileWidth, int tileHeight, int gemType) {
        switch (gemType) {
            case Gem.ALUMINIUM: drawRedSquare(g,x,y,tileWidth,tileHeight);
                break;
            case Gem.BRONZE: drawBlueCircle(g,x,y,tileWidth,tileHeight);
                break;
            case Gem.CHROMIUM: drawGreenUpTriangle(g,x,y,tileWidth,tileHeight);
                break;
            case Gem.DIAMOND: drawYellowDownTriangle(g,x,y,tileWidth,tileHeight);
                break;
            case Gem.EMERALD: drawMagentaDiamond(g,x,y,tileWidth,tileHeight);
                break;
            case Gem.FELDSPAR: drawPurpleOval(g,x,y,tileWidth,tileHeight);
                break;
            case Gem.GOLD: drawBrownRectangle(g,x,y,tileWidth, tileHeight);
                break;
            default: break; // null case
        }
    }

    // EFFECTS: draws a red square
    private void drawRedSquare(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(Color.RED);
        g.fillRect(x + tileWidth / 8, y + tileHeight / 8, tileWidth * 3 / 4, tileHeight * 3 / 4);
    }

    // EFFECTS: draws a blue circle
    private void drawBlueCircle(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(Color.BLUE);
        g.fillOval(x + tileWidth / 8, y + tileHeight / 8, tileWidth * 3 / 4, tileHeight * 3 / 4);
    }

    // EFFECTS: draws a green rightside up triangle
    private void drawGreenUpTriangle(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(Color.GREEN);
        g.fillPolygon(new int[]{x + tileWidth / 2, x + tileWidth / 8, x + tileWidth * 7 / 8},
                new int[]{y + tileHeight / 8, y + tileHeight * 7 / 8, y + tileHeight * 7 / 8}, 3);
    }

    // EFFECTS: draws a yellow upside down triangle
    private void drawYellowDownTriangle(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(Color.YELLOW);
        g.fillPolygon(new int[]{x + tileWidth / 2, x + tileWidth / 8, x + tileWidth * 7 / 8},
                new int[]{y + tileHeight * 7 / 8, y + tileHeight / 8, y + tileHeight / 8}, 3);
    }

    // EFFECTS: draws a magenta diamond
    private void drawMagentaDiamond(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(Color.MAGENTA);
        g.fillPolygon(new int[]{x + tileWidth / 2, x + tileWidth / 8, x + tileWidth / 2, x + tileWidth * 7 / 8},
                new int[]{y + tileHeight / 8, y + tileHeight / 2, y + tileHeight * 7 / 8, y + tileHeight / 2}, 4);
    }

    // EFFECTS: draws a purple oval
    private void drawPurpleOval(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(new Color(140, 140, 255));
        g.fillOval(x + tileWidth / 8, y + tileHeight / 4, tileWidth * 3 / 4, tileHeight / 2);
    }

    // EFFECTS: draws a brown rectangle
    private void drawBrownRectangle(Graphics g, int x, int y, int tileWidth, int tileHeight) {
        g.setColor(new Color(140, 90, 10));
        g.fillRect(x + tileWidth / 8, y + tileHeight / 4, tileWidth * 3 / 4, tileHeight / 2);
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
