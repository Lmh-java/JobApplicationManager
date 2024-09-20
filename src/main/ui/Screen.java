package ui;

import java.util.InputMismatchException;

/**
 * Screen represents a screen (a single page) of the console application
 */
public abstract class Screen {

    // EFFECTS: print the prompt message and handle user input
    protected void handleInput(String message) {
        System.out.print(message + " >>>");
    }

    // EFFECTS: start to run the current screen
    protected abstract void run();

    // EFFECTS: reads an integer from the console
    protected int readInt() {
        int result = -100;
        try {
            result = ApplicationTrackerApp.SCANNER.nextInt();
        } catch (InputMismatchException e) {
            // if the input is not a number, give user the alert
            System.out.print("Input must be an integer! ");
        }
        // consume the rest of the line
        ApplicationTrackerApp.SCANNER.nextLine();
        return result;
    }

    // EFFECTS: reads a string from the console
    protected String readString() {
        return ApplicationTrackerApp.SCANNER.nextLine();
    }

    // EFFECTS: reads a string from the console, if a blank string is inputted, use the default value.
    protected String readString(String defaultValue) {
        String result = ApplicationTrackerApp.SCANNER.nextLine();
        if (result.trim().isBlank()) {
            return defaultValue;
        }
        return result;
    }
}
