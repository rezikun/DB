package form;

import entities.Column;
import entities.Table;
import entities.types.Type;
import service.DBService;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.List;

public class TablePane extends JPanel implements TableModelListener, CellEditorListener {
    private static TablePane instance;
    private JTable table;

    private boolean DEBUG = true;

    private TablePane() {
        super(new GridLayout(1,0));
        table = new JTable(new TableModel());
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.getModel().addTableModelListener(this);
        table.getDefaultEditor(String.class).addCellEditorListener(this);

        setVisible(false);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public static TablePane getInstance() {
        if (instance == null) {
            instance = new TablePane();
        }
        return instance;
    }

    public JTable getTable() {
        return instance.table;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

        JOptionPane.showMessageDialog(getParent(),
                "in tableChanged");
        // Do something with the data...
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        JOptionPane.showMessageDialog(getParent(),
                e.getSource());
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        JOptionPane.showMessageDialog(getParent(),
                "in editingCanceled");
    }

    class TableModel extends AbstractTableModel {
        private String tableName = null;
        private List<String> columnNames = null;
        private List<List<String>> data = null;
        private List<Class> columnClasses = null;

        public TableModel() {
            this.columnNames = List.of("Example");
            this.data = List.of(List.of("1"), List.of("2"));
        }

        public TableModel(String tableName, List<List<String>> rows, List<String> columns, List<Class> classes) {
            this.tableName = tableName;
            this.data = rows;
            this.columnNames = columns;
            this.columnClasses = classes;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex).get(columnIndex);
        }

        @Override
        public String getColumnName(int index) {
            return columnNames.get(index);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return columnClasses.get(columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return true;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            String cellDisplay = DBService.setCellInTable(tableName, row, col, value);
            data.get(row).set(col, cellDisplay);
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data.get(i).get(j));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    public void setTableData(String name) {
        setVisible(true);
        Table t = DBService.getTable(name);
        TableModel tableModel = (TableModel) table
                .getModel();
        table.setModel(new TableModel(name, t.getRows(), t.getColumnsName(), t.getViewTypes()));
        tableModel.fireTableStructureChanged();
    }

}
