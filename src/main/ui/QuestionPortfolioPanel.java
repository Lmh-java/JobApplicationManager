package ui;

import model.*;

import javax.swing.*;

/**
 * The Panel for question portfolio
 */
public class QuestionPortfolioPanel extends JPanel {

    // main table for all applications
    private final JTable table;
    private QuestionTableModel tableModel;
    private final JScrollPane scrollPane;

    // layout
    private final BoxLayout mainLayout;
    private final JPanel buttonPanel;
    private final BoxLayout buttonLayout;

    // buttons
    private final JButton createButton;
    private final JButton modifyButton;
    private final JButton removeButton;

    // portfolio
    private QuestionTemplatePortfolio portfolio;

    // EFFECTS: initialize this panel
    public QuestionPortfolioPanel(QuestionTemplatePortfolio portfolio) {
        this.portfolio = portfolio;
        tableModel = new QuestionTableModel(portfolio);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        buttonPanel = new JPanel();
        mainLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        buttonLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);

        createButton = new JButton("Create");
        modifyButton = new JButton("Modify");
        removeButton = new JButton("Remove");
        setUp();
    }

    // MODIFIES: this
    // EFFECTS: set up the ui layout
    private void setUp() {
        table.setFillsViewportHeight(true);
        setLayout(mainLayout);
        add(scrollPane);
        add(buttonPanel);

        buttonPanel.setLayout(buttonLayout);
        buttonPanel.add(createButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(removeButton);
        buttonPanel.setAlignmentY(0);

        createButton.addActionListener(e -> createClicked());
        modifyButton.addActionListener(e -> modifyClicked());
        removeButton.addActionListener(e -> removeClicked());
    }

    // MODIFIES: this
    // EFFECTS: update this portfolio to another object
    public void updatePortfolio(QuestionTemplatePortfolio portfolio) {
        this.portfolio = portfolio;
        tableModel = new QuestionTableModel(portfolio);
        table.setModel(tableModel);
        table.updateUI();
    }

    // MODIFIES: this
    // EFFECTS: create button clicked, show the create window
    private void createClicked() {
        QuestionTemplateDialogue dialogue = new QuestionTemplateDialogue(
                null,
                (QuestionAndAnswerItem item) -> {
                    portfolio.add(item);
                    table.updateUI();
                    return null;
                });
        dialogue.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: create button clicked, show the modify window
    private void modifyClicked() {
        QuestionAndAnswerItem item = getSelected();
        if (item == null) {
            return;
        }

        QuestionTemplateDialogue dialogue = new QuestionTemplateDialogue(
                item,
                (QuestionAndAnswerItem p) -> {
                    portfolio.modify(p);
                    table.updateUI();
                    return null;
                });
        dialogue.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: remove button clicked, then remove the selected item
    private void removeClicked() {
        QuestionAndAnswerItem item = getSelected();
        if (item == null) {
            return;
        }
        portfolio.delete(item.getId());
        table.updateUI();
    }

    // EFFECTS: returns the selected application
    private QuestionAndAnswerItem getSelected() {
        int selectedRowNum = table.getSelectedRow();
        QuestionAndAnswerItem item = null;
        try {
            item = portfolio.getList().get(selectedRowNum);
        } catch (IndexOutOfBoundsException ex) {
            // ignore
        }
        return item;
    }

}
