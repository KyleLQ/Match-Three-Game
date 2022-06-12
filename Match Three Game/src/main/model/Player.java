package model;

import java.util.Arrays;

// represents the player that is playing on the board, has points earned
public class Player {
    public static final int PTS_FOR_REMOVE_ROW = 15;

    private int points;

    // EFFECTS: constructs a new player with 0 points
    public Player() {
        points = 0;
    }

    // REQUIRES: row is in [0,gems.length-1]
    // MODIFIES: this, gems
    // EFFECTS: if this player's points >= PTS_FOR_REMOVE_ROW:
    //              - subtract PTS_FOR_REMOVE_ROW from this player's points, remove all gems
    //                from row row of gems, return true
    //              - else, only return false
    public Boolean removeRow(Gem[][] gems, int row) {
        if (points >= PTS_FOR_REMOVE_ROW) {
            points -= PTS_FOR_REMOVE_ROW;
            Arrays.fill(gems[row], null);
            for (int col = 0; col < gems[row].length; col++) { // to keep consistent with other log entries
                EventLog.getInstance().logEvent(new Event("at row: " + row + ", col: " + col + ", Removed gem"));
            }
            EventLog.getInstance().logEvent(new Event("Cleared row " + row + " of all gems"));
            return true;
        } else {
            return false;
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
