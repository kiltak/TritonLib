package view;

import javax.swing.table.AbstractTableModel;

import triton.model.sound.Bank;

public class MyTableModel extends AbstractTableModel {
    private String[] columnNames = Bank.BANK_NAMES[Bank.PROG];
    private Object[][] data = {
            { "1", "2", "3", "2", "3", "2", "3", "2", "3", "2", "3", "2", "Awesome" },
            { "4", "5", "6", "2", "3", "2", "3", "2", "3", "2", "3", "2", "Chris" }};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's editable.
     */
    public boolean isCellEditable(int row, int col) {
        // Note that the data/cell address is constant,
        // no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
