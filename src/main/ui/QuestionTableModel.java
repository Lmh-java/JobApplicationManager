package ui;

import model.*;

import javax.swing.table.AbstractTableModel;

/**
 * A table model for question portfolio for JTable
 */

public class QuestionTableModel extends AbstractTableModel {

    // column names
    private static final String[] COLUMN_NAMES = {"Question", "Answer"};
    private final QuestionTemplatePortfolio portfolio;

    // EFFECTS: initialize an question template table model
    public QuestionTableModel(QuestionTemplatePortfolio portfolio) {
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
        QuestionAndAnswerItem item = portfolio.getList().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getQuestion();
            case 1:
                return item.getAnswer();
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
