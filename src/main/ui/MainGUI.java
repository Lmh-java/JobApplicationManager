package ui;

/**
 * GUI Application Entrance
 */
public class MainGUI {
    // main enter point of the GUI Application
    public static void main(String[] args) {
        // start a splash screen, this will block the thread.
        new StartingSplashScreen().displaySplashScreen();
        // start the main program
        new MainView();
    }
}
