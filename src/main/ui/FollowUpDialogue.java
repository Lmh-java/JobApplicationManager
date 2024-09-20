package ui;

import model.ApplicationPortfolio;

import javax.swing.*;


/**
 * Dialog that shows all follow-up information
 */
public class FollowUpDialogue extends JDialog {

    // geometry of the window
    private static final int INITIAL_WIDTH = 500;
    private static final int INITIAL_HEIGHT = 300;

    private final JTable table;
    private final ApplicationTableModel tableModel;

    // EFFECTS: create a follow-up dialog with given portfolio
    public FollowUpDialogue(ApplicationPortfolio portfolio) {
        tableModel = new ApplicationTableModel(portfolio);
        table = new JTable(tableModel);
        setUp();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up the layout
    private void setUp() {
        setBounds(100, 100, INITIAL_WIDTH, INITIAL_HEIGHT);
        this.getContentPane().add(new JScrollPane(table), SwingConstants.CENTER);
        table.setFillsViewportHeight(true);
        setTitle("These are applications that you need to follow up today!");
    }
}