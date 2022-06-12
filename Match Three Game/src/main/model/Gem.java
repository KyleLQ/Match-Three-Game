package model;

// represents a gem that is found on the board; is one of several gem types
public class Gem {
    public static final int ALUMINIUM = 1; // red square
    public static final int BRONZE = 2;    // blue circle
    public static final int CHROMIUM = 3;  // green right side up triangle
    public static final int DIAMOND = 4;   // yellow upside down triangle
    public static final int EMERALD = 5;   // magenta diamond
    public static final int FELDSPAR = 6;  // purple oval
    public static final int GOLD = 7;      // Brown Rectangle

    private int type;

    // REQUIRES: type must be one of the constant values listed above.
    // EFFECTS: constructs a new gem with a type given by the param
    public Gem(int type) {
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
