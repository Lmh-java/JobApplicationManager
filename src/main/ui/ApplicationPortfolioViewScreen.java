package ui;

import model.Application;
import model.Containable;
import model.PositionApplication;

import java.util.List;

/**
 * ApplicationPortfolioViewScreen represents a console page for viewing application portfolio
 */
public class ApplicationPortfolioViewScreen extends Screen {

    // selected portfolio
    private Containable<PositionApplication> portfolio;

    // has the user enter quit command
    private boolean quit;

    // REQUIRES: portfolio != null
    // EFFECTS: create an instance of this class with the given portfolio
    public ApplicationPortfolioViewScreen(Containable<PositionApplication> portfolio) {
        this.portfolio = portfolio;
    }

    // EFFECTS: handle user input and trigger corresponding functions
    @Override
    public void handleInput(String message) {
        super.handleInput(message);
        String userInput = readString();
        switch (userInput) {
            case "add":
                createItem();
                break;
            case "del":
                deleteItem();
                break;
            case "mod":
                modifyItem();
                break;
            case "fol":
                followUp();
                break;
            case "quit":
                quit = true;
                return;
            default:
                System.out.println("Unknown Command!");
                break;
        }
    }

    // EFFECTS: starts to render current screen
    @Override
    public void run() {
        while (ApplicationTrackerApp.getAppStatus() && !quit) {
            renderPortfolio();
            handleInput(String.format("(%s) Type [add] to create, [del] to delete, [mod] to modify, [fol] to"
                            + " examine all needed follow-up, [quit] to quit",
                    portfolio.getName()));
        }
    }

    // EFFECTS: renders the current portfolio
    public void renderPortfolio() {
        List<String> lines = portfolio.getDisplayString();
        if (lines.size() == 0) {
            System.out.println("Portfolio is empty, Type [add] to create a new item");
        } else {
            System.out.printf("(%s listview) All applications in the portfolio: \n", portfolio.getName());
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: delete item in portfolio
    private void deleteItem() {
        System.out.printf("(%s delete) Type id (or prefix) of the application being deleted >>>", portfolio.getName());
        String id = readString();
        Application app = portfolio.searchByIdPrefix(id);
        if (app == null) {
            System.out.printf("(%s delete) Item not found \n", portfolio.getName());
            return;
        }
        if (portfolio.delete(app.getId())) {
            System.out.printf("(%s delete) Successfully deleted \n", portfolio.getName());
        } else {
            System.out.printf("(%s delete) Item not found \n", portfolio.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: modify items in the portfolio
    private void modifyItem() {
        System.out.printf("(%s modify) Type id (or prefix) of the application >>>", portfolio.getName());
        String id = readString();
        PositionApplication app = portfolio.searchByIdPrefix(id);
        // if application with given id is not contained in the portfolio, it will return null
        if (app == null) {
            System.out.printf("(%s modify) Item with id %s does not found \n", portfolio.getName(), id);
            return;
        }
        new CreateAndEditApplicationScreen(app).run();
    }

    // MODIFIES: this
    // EFFECTS: display the interface and adds item to the portfolio
    private void createItem() {
        CreateAndEditApplicationScreen screen = new CreateAndEditApplicationScreen(null);
        screen.run();
        if (screen.getApplication() != null) {
            portfolio.add(screen.getApplication());
        }
    }

    // EFFECTS: render all applications that needs to follow up on the screen
    public void followUp() {
        // is there a single application to follow up?
        boolean hasOne = false;
        for (PositionApplication positionApplication : portfolio.getList()) {
            if (positionApplication.followUpNeeded()) {
                hasOne = true;
                System.out.println(positionApplication.getDisplayName());
            }
        }
        if (!hasOne) {
            System.out.println("(Portfolio Follow Up) Everything is cool, no need to follow up today!");
        }
    }
}
