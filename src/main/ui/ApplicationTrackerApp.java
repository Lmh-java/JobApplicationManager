package ui;

import model.*;
import persistence.JsonLoader;
import persistence.JsonSaver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * ApplicationTrackerApp represents the entire application
 */
public class ApplicationTrackerApp extends Screen {

    // list of all portfolios
    // for now, we only have two portfolio, one is for applications and another is for questions and answers
    private ArrayList<Containable<?>> portfolioList;

    // Keyboard scanner to read keyboard input
    public static final Scanner SCANNER = new Scanner(System.in);


    // state of app
    private static boolean isAppRunning;

    // EFFECTS: create an instance of the app with an empty list of portfolio
    public ApplicationTrackerApp() {
        portfolioList = new ArrayList<>();
        isAppRunning = false;
    }

    // MODIFIES: this
    // EFFECTS: start the main loop of the
    public void run() {
        isAppRunning = true;
        // put instances of portfolios
        portfolioList.add(new ApplicationPortfolio("Application Portfolio"));
        portfolioList.add(new QuestionTemplatePortfolio("Question Template Portfolio"));
        // print welcome information
        printWelcome();
        while (isAppRunning) {
            listAllPortfolios();
            handleInput("(Portfolio) Type index number to select portfolio, "
                    + "[-1] to save current data, [-2] to load saved data, [-3] to exit");
        }
        // when the program stops running, close all the streams
        SCANNER.close();
    }

    // EFFECTS: print welcome information when the starting the app
    private void printWelcome() {
        System.out.println("Job Application Tracker");
    }

    // EFFECTS: print a list of all portfolios
    private void listAllPortfolios() {
        System.out.println("(Portfolio) All available portfolios: ");
        int index = 0;
        for (Containable<?> portfolio : portfolioList) {
            System.out.printf("[%d] %s \n", index, portfolio.getName());
            index++;
        }
    }

    // MODIFIES: ApplicationTrackerApp.isAppRunning
    // EFFECTS: print the message as the prompt and wait for user inputs
    protected void handleInput(String message) {
        super.handleInput(message);
        int userInput = readInt();
        switch (userInput) {
            case 0:
                new ApplicationPortfolioViewScreen((Containable<PositionApplication>) portfolioList.get(0)).run();
                break;
            case 1:
                new QuestionPortfolioViewScreen((Containable<QuestionAndAnswerItem>) portfolioList.get(1)).run();
                break;
            case -1:
                saveData();
                break;
            case -2:
                loadSavedData();
                break;
            case -3:
                setAppStatus(false);
                System.exit(0);
            default:
                // input invalid index number
                System.out.println("Invalid Input!");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: load saved data to the program
    private void loadSavedData() {
        JsonLoader appLoader = new JsonLoader("./data/application.json");
        JsonLoader questionLoader = new JsonLoader("./data/question.json");
        portfolioList.clear();
        ApplicationPortfolio applicationPortfolio;
        QuestionTemplatePortfolio questionTemplatePortfolio;
        boolean exceptionThrown = false;
        // load application data
        try {
            applicationPortfolio = appLoader.parseApplicationPortfolio(appLoader.loadJsonObject());
            portfolioList.add(applicationPortfolio);
        } catch (IOException e) {
            System.err.println("Failed to load application data");
            exceptionThrown = true;
        }
        // load question templates
        try {
            questionTemplatePortfolio = questionLoader.parseQuestionTemplatePortfolio(questionLoader.loadJsonObject());
            portfolioList.add(questionTemplatePortfolio);
        } catch (IOException e) {
            System.err.println("Failed to load question template data");
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.out.println("Successfully load question template data");
        }
    }

    // EFFECTS: save data to local file
    private void saveData() {
        JsonSaver appSaver = new JsonSaver("./data/application.json");
        JsonSaver questionSaver = new JsonSaver("./data/question.json");
        try {
            appSaver.saveToFile(((ApplicationPortfolio) portfolioList.get(0)).toJson());
            questionSaver.saveToFile(((QuestionTemplatePortfolio) portfolioList.get(1)).toJson());
            System.out.println("Data successfully saved");
        } catch (FileNotFoundException e) {
            System.err.println("Failed to save current data!");
        }
    }

    // EFFECTS: returns current app status. If the app is running then return true, otherwise false.
    public static boolean getAppStatus() {
        return ApplicationTrackerApp.isAppRunning;
    }

    // MODIFIES: ApplicationTrackerApp.isAppRunning
    // EFFECTS: updates the application status with new status
    public static void setAppStatus(boolean status) {
        ApplicationTrackerApp.isAppRunning = status;
    }
}
