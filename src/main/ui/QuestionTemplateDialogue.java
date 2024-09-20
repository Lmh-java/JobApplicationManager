package ui;

import model.QuestionAndAnswerItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.function.Function;

/**
 * A dialogue for creating and modifying question template
 */
public class QuestionTemplateDialogue extends JDialog {

    // result item
    private final QuestionAndAnswerItem originalItem;

    private final Function<QuestionAndAnswerItem, Void> callback;

    // the main layout of the dialogue
    private final GridBagLayout mainLayout;

    // main panel
    private final JPanel panel;

    // components
    private final JButton submitButton;
    private final JButton cancelButton;
    private final JTextArea questionText;
    private final JTextArea answerText;

    // EFFECTS: initialize the dialog
    // NOTE: if item == null, it means the dialogue is called for creating a new question item,
    //       otherwise, it is modifying an existing question item.
    public QuestionTemplateDialogue(QuestionAndAnswerItem item, Function<QuestionAndAnswerItem, Void> callback) {
        this.originalItem = item;
        this.callback = callback;

        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        questionText = new JTextArea(50, 50);
        answerText = new JTextArea(50, 50);
        panel = new JPanel();
        mainLayout = new GridBagLayout();

        // set up the UI
        setUp();
        autoFillInfo();
    }

    // MODIFIES: this
    // EFFECTS: set up the layout
    private void setUp() {
        this.setResizable(false);
        setBounds(100, 100, 600, 400);
        this.getContentPane().add(panel, SwingConstants.CENTER);
        this.panel.setBorder(new EmptyBorder(10, 10, 10,10));
        panel.setLayout(mainLayout);
        addComponent(new JLabel("Question: "), panel, 0, 0, 1, 2, GridBagConstraints.NONE, 1, 1);
        addComponent(new JScrollPane(questionText), panel, 0, 1, 3, 2, GridBagConstraints.BOTH, 5, 2);
        addComponent(new JLabel("Answer: "), panel, 2, 0, 1, 2, GridBagConstraints.NONE, 1, 1);
        addComponent(new JScrollPane(answerText), panel, 2, 1, 3, 2, GridBagConstraints.BOTH, 5, 2);
        addComponent(submitButton, panel, 4, 0, 1, 1, GridBagConstraints.NONE, 0, 0);
        addComponent(cancelButton, panel, 4, 1, 1, 1, GridBagConstraints.NONE, 0, 0);
        submitButton.addActionListener(e -> submitClicked());
        cancelButton.addActionListener(e -> cancelClicked());

        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(false);
        answerText.setLineWrap(true);
        answerText.setWrapStyleWord(false);
    }

    // MODIFIES: this
    // EFFECTS: autofill the info into the fields when we are modifying
    private void autoFillInfo() {
        if (this.originalItem != null) {
            questionText.setText(originalItem.getQuestion());
            answerText.setText(originalItem.getAnswer());
        }
    }

    // MODIFIES: this
    // EFFECTS: the submit button is clicked, submit the changes
    private void submitClicked() {
        QuestionAndAnswerItem item = new QuestionAndAnswerItem(
                questionText.getText(),
                answerText.getText()
        );
        if (this.originalItem != null) {
            item.setId(this.originalItem.getId());
        }
        this.callback.apply(item);
        this.setVisible(false);
        this.dispose();
    }

    // MODIFIES: this
    // EFFECTS: the cancel button is clicked
    private void cancelClicked() {
        this.setVisible(false);
        this.dispose();
    }

    // REQUIRES: component != null, container != null, row >= 0, col >= 0, gridWidth >=0, gridHeight >= 0
    // MODIFIES: this
    // EFFECTS: add one component to the layout with specified arguments
    private void addComponent(Component component, Container container,
                              int row, int col, int gridWidth,
                              int gridHeight, int fill, double factorX, double factorY) {
        GridBagConstraints c = new GridBagConstraints(
                col,
                row,
                gridWidth,
                gridHeight,
                factorX,
                factorY,
                GridBagConstraints.NORTHWEST,
                fill,
                new Insets(10, 10, 10, 10),
                10,
                10
        );
        container.add(component, c);
    }
}
