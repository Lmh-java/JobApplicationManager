package ui;

import javax.swing.*;
import java.awt.*;

/**
 * A starting slash screen for the GUI system
 */
public class StartingSplashScreen extends JWindow {

    // height of the logo
    private static final int LOGO_HEIGHT = 560;
    // width of the logo
    private static final int LOGO_WIDTH = 512;

    // MODIFIES: this
    // EFFECTS: initialize a splash screen
    public StartingSplashScreen() {
        JPanel panel = new JPanel();
        this.getContentPane().add(panel, SwingConstants.CENTER);

        panel.add(new JLabel("", new ImageIcon("data/Logo.png"), SwingConstants.CENTER),
                BorderLayout.CENTER);
        panel.add(
                new JLabel("Job Application System is Running"),
                BorderLayout.SOUTH
        );
    }

    // MODIFIES: this
    // EFFECTS: display the splash screen
    public void displaySplashScreen() {
        this.setBounds(
                getScreenCenterX() - LOGO_WIDTH / 2,
                getScreenCenterY() - LOGO_HEIGHT / 2,
                LOGO_WIDTH,
                LOGO_HEIGHT
        );
        this.setVisible(true);

        // wait for 5 seconds and then close
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.setVisible(false);
        this.dispose();
    }

    // EFFECTS: returns the x coordinate of the window
    private static int getScreenCenterX() {
        return Toolkit.getDefaultToolkit().getScreenSize().width / 2;
    }

    // EFFECTS: returns the y coordinate of the window
    private static int getScreenCenterY() {
        return Toolkit.getDefaultToolkit().getScreenSize().height / 2;
    }
}
