package ui;

import model.Containable;
import model.QuestionAndAnswerItem;

import java.util.List;

/**
 * QuestionPortfolioViewScreen represents a question portfolio view page
 */
public class QuestionPortfolioViewScreen extends Screen {

    // selected portfolio
    private Containable<QuestionAndAnswerItem> portfolio;

    // has user inputted quit command to quit to last menu
    private boolean quit = false;

    // REQUIRES: portfolio != null
    // EFFECTS: create an instance of this class with the given portfolio
    public QuestionPortfolioViewScreen(Containable<QuestionAndAnswerItem> portfolio) {
        this.portfolio = portfolio;
    }

    // EFFECTS: start to run the current screen
    @Override
    public void run() {
        while (ApplicationTrackerApp.getAppStatus() && !quit) {
            renderPortfolio();
            handleInput(String.format("(%s) Type [add] to create, [del] to delete, [quit] to quit",
                    portfolio.getName()));
        }
    }

    // EFFECTS: renders the current portfolio
    public void renderPortfolio() {
        List<String> lines = portfolio.getDisplayString();
        if (lines.size() == 0) {
            System.out.println("Portfolio is empty, Type [add] to create a new item");
        } else {
            System.out.printf("(%s listview) All Q&As in the portfolio: \n", portfolio.getName());
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: ask for different info with the prompt message and handle user input
    @Override
    public void handleInput(String message) {
        super.handleInput(message);
        String userInput = readString();
        switch (userInput) {
            case "add":
                createQuestion();
                break;
            case "del":
                deleteQuestion();
                break;
            case "quit":
                quit = true;
                return;
            default:
                System.out.println("Unknown Command!");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: display the page for creating template question and insert a new question and answer to the portfolio
    private void createQuestion() {
        CreateQuestionScreen screen = new CreateQuestionScreen();
        screen.run();
        portfolio.add(screen.getItem());
    }

    // MODIFIES: this
    // EFFECTS: prompt for template question id and remove it from the portfolio
    private void deleteQuestion() {
        System.out.printf("(%s delete) Type id (or prefix) of the template question being deleted >>>",
                portfolio.getName());
        String id = readString();
        QuestionAndAnswerItem app = portfolio.searchByIdPrefix(id);
        if (app == null) {
            System.out.printf("(%s delete) Item not found \n", portfolio.getName());
            return;
        }
        if (portfolio.delete(app.getId())) {
            System.out.printf("(%s delete) Successfully deleted \n", portfolio.getName());
        }
    }
}
