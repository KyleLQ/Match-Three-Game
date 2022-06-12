package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// A frame used in the match three game, to be extended by other frames
public abstract class GeneralFrame extends JFrame {

    // this constructor is based on constructor from SimpleDrawingPlayer DrawingEditor class
    // link:https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete/blob/master/src/ui/DrawingEditor.java

    // EFFECTS: initializes the screen
    public GeneralFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(MainGUI.FRAME_WIDTH, MainGUI.FRAME_HEIGHT);
        this.setResizable(false);
        this.setLocation(400, 0);
        ImageIcon img = new ImageIcon(MainGUI.LOGO_LOCATION);
        this.setIconImage(img.getImage());
        this.setLayout(new BorderLayout());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
    }

    // EFFECTS: returns a JPanel that is empty, with grid layout
    protected JPanel initializeButtons() {
        JPanel bottomButtons = new JPanel();
        bottomButtons.setLayout(new GridLayout(1,1));
        return bottomButtons;
    }

    // EFFECTS: initializes a button with given title and listener.
    //          totalButtons is total number of buttons in that screen.
    protected JButton initializeButton(String buttonText, int totalButtons, ActionListener listener) {
        JButton button = new JButton();
        button.setText(buttonText);
        button.setPreferredSize(new Dimension(MainGUI.FRAME_WIDTH / totalButtons, MainGUI.BUTTON_HEIGHT));
        button.addActionListener(listener);
        return button;
    }

    // based on printLog method in ScreenPrinter class, from AlarmSystem project given in phase 4
    // https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

    // EFFECTS: prints the events in the el
    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}
