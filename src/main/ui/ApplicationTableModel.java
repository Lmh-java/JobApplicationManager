package ui;

import model.Application;
import model.ApplicationPortfolio;
import model.PositionApplication;

import javax.swing.table.AbstractTableModel;

/**
 * A table model for application portfolio for JTable
 */

public class ApplicationTableModel extends AbstractTableModel {

    // column names
    private static final String[] COLUMN_NAMES = {"Organization", "Position", "Submit date",
            "Status", "Follow up", "Info link"};
    private final ApplicationPortfolio portfolio;

    // EFFECTS: initialize an application table model
    public ApplicationTableModel(ApplicationPortfolio portfolio) {
        this.portfolio = portfolio;
    }

    // EFFECTS: returns number of rows
    @Override
    public int getRowCount() {
        return portfolio.getNum();
    }

    // EFFECTS: returns number of columns
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    // EFFECTS: returns the value at the given row index and column index
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Application app = portfolio.getList().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return ((PositionApplication) app).getOrganization();
            case 1:
                return ((PositionApplication) app).getPosition();
            case 2:
                return app.getSubmitDate();
            case 3:
                return PositionApplicationDialogue.STATUS[app.getStatus() + 2];
            case 4:
                return app.getFollowUpDate();
            case 5:
                return app.getInfoLink();
            default:
                return null;
        }
    }

    // EFFECTS: returns the column name at the given column index
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
}
