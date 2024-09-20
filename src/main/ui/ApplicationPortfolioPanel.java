package ui;

import model.Application;
import model.ApplicationPortfolio;
import model.PositionApplication;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Application Portfolio Panel
 */
public class ApplicationPortfolioPanel extends JPanel {

    // main table for all applications
    private JTable table;
    private ApplicationTableModel tableModel;
    private final JScrollPane scrollPane;

    // layout
    private final BoxLayout mainLayout;
    private final JPanel buttonPanel;
    private final BoxLayout buttonLayout;

    // buttons
    private final JButton createButton;
    private final JButton modifyButton;
    private final JButton removeButton;
    private final JButton followButton;

    // portfolio
    private ApplicationPortfolio portfolio;

    // EFFECTS: initialize this panel
    public ApplicationPortfolioPanel(ApplicationPortfolio portfolio) {
        this.portfolio = portfolio;
        setUpTable();
        scrollPane = new JScrollPane(table);
        buttonPanel = new JPanel();
        mainLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        buttonLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        createButton = new JButton("Create");
        modifyButton = new JButton("Modify");
        removeButton = new JButton("Remove");
        followButton = new JButton("Follow Up");
        setUp();
    }

    // MODIFIES: this
    // EFFECTS: set up the table component
    private void setUpTable() {
        tableModel = new ApplicationTableModel(portfolio);
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                if (table.getSelectedRow() == row) {
                    return comp;
                }
                if (portfolio.getNum() != 0
                        && ((PositionApplication) portfolio.getList().get(row)).getIsResearchApplication()) {
                    comp.setBackground(Color.YELLOW);
                } else {
                    comp.setBackground(Color.WHITE);
                }
                return comp;
            }
        };
        table.setFillsViewportHeight(true);
    }

    // MODIFIES: this
    // EFFECTS: set up the ui layout
    private void setUp() {
        setLayout(mainLayout);
        add(scrollPane);
        add(buttonPanel);

        buttonPanel.setLayout(buttonLayout);
        buttonPanel.add(createButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(followButton);
        buttonPanel.setAlignmentY(0);

        createButton.addActionListener(e -> createClicked());
        modifyButton.addActionListener(e -> modifyClicked());
        removeButton.addActionListener(e -> removeClicked());
        followButton.addActionListener(e -> followClicked());
    }

    // MODIFIES: this
    // EFFECTS: update this portfolio to another object
    public void updatePortfolio(ApplicationPortfolio portfolio) {
        this.portfolio = portfolio;
        tableModel = new ApplicationTableModel(portfolio);
        table.setModel(tableModel);
        table.updateUI();
    }

    // MODIFIES: this
    // EFFECTS: create button clicked, show the create window
    private void createClicked() {
        PositionApplicationDialogue dialogue = new PositionApplicationDialogue(
                null,
                (PositionApplication app) -> {
                    portfolio.add(app);
                    table.updateUI();
                    return null;
                });
        dialogue.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: create button clicked, show the modify window
    private void modifyClicked() {
        PositionApplication app = getSelected();
        if (app == null) {
            return;
        }

        PositionApplicationDialogue dialogue = new PositionApplicationDialogue(
                app,
                (PositionApplication p) -> {
                    portfolio.modify(p);
                    table.updateUI();
                    return null;
                });
        dialogue.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: remove button clicked, then remove the selected item
    private void removeClicked() {
        PositionApplication app = getSelected();
        if (app == null) {
            return;
        }
        portfolio.delete(app.getId());
        table.updateUI();
    }

    // EFFECTS: remove button clicked, then remove the selected item
    private void followClicked() {
        ApplicationPortfolio followUpPortfolio = getAllFollowUps();
        if (followUpPortfolio.getNum() > 0) {
            new FollowUpDialogue(followUpPortfolio);
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to do today!", "Yep",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: get a portfolio with all follow-ups
    private ApplicationPortfolio getAllFollowUps() {
        ApplicationPortfolio resultPortfolio = new ApplicationPortfolio("Temporary Portfolio");
        for (Application positionApplication : portfolio.getList()) {
            if (positionApplication.followUpNeeded()) {
                resultPortfolio.add(positionApplication);
            }
        }
        return resultPortfolio;
    }

    // EFFECTS: returns the selected application
    private PositionApplication getSelected() {
        int selectedRowNum = table.getSelectedRow();
        Application app = null;
        try {
            app = portfolio.getList().get(selectedRowNum);
        } catch (IndexOutOfBoundsException ex) {
            // ignore
        }
        return (PositionApplication) app;
    }
}
