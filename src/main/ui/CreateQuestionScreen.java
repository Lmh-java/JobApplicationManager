package ui;

import model.QuestionAndAnswerItem;

/**
 * CreateQuestionScreen represents a console screen for creating question template
 */
public class CreateQuestionScreen extends Screen {

    private final QuestionAndAnswerItem item;

    // EFFECTS: create an instance of this class with empty question and answer
    public CreateQuestionScreen() {
        item = new QuestionAndAnswerItem("", "");
    }

    // EFFECTS: start to run the current screen
    @Override
    public void run() {
        handleInput("(Question Template Create) Please input the following information");
    }

    // MODIFIES: this
    // EFFECTS: ask for user input and fill the item QuestionAndAnswerItem object
    @Override
    public void handleInput(String message) {
        System.out.println(message);
        System.out.println("(Question Template Create) Type out the question >>>");
        item.setQuestion(ApplicationTrackerApp.SCANNER.nextLine());
        System.out.println("(Question Template Create) Type out the answer >>>");
        item.setAnswer(ApplicationTrackerApp.SCANNER.nextLine());
    }

    // EFFECTS: returns the constructed question and answer item
    public QuestionAndAnswerItem getItem() {
        return item;
    }
}
