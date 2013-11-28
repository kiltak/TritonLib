package view;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import triton.model.sound.Bank;

public class NameChartPanel extends JPanel implements TableModelListener {
    public NameChartPanel () {
        JTable table = new JTable (new MyTableModel());
        
        JScrollPane scrollPane = new JScrollPane (table);
        
        // Be able to select at most two entries
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
        table.setFillsViewportHeight(true);
        
        // Listen for data changes
        table.getModel().addTableModelListener(this);
        
        this.add(scrollPane);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        System.out.println ("table changed " + e);
        System.out.println ("row " + row + ", col " + column);
        
        // TODO: Send update to controller
    }
}
