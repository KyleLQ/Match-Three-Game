package ui;

// Main class to start the GUI application, contains constants shared amongst all windows
public class MainGUI {
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 1000;
    public static final int BUTTON_HEIGHT = 40;
    public static final int BOARD_SIDE_LENGTH = 8;
    public static final String SAVE_FILE_LOCATION = "./data/savedGameState.json";
    public static final String LOGO_LOCATION = "./data/logo.jpg";

    // EFFECTS: starts GUI game by constructing new TitleFrame object
    public static void main(String[] args) {
        new TitleFrame();
    }
}
