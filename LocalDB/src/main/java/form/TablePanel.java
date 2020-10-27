package form;

import entities.Table;
import form.popupMenus.table.PopupTableListener;
import form.popupMenus.tree.PopupTreeListener;
import service.DBService;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TablePanel extends JPanel implements TableModelListener, CellEditorListener {
    private static TablePanel instance;
    private final JTable table;
    private String tableName;

    private boolean DEBUG = true;

    private TablePanel() {
        super(new GridLayout(1,0));
        table = new JTable(new TableModel());
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.getModel().addTableModelListener(this);
        table.getDefaultEditor(String.class).addCellEditorListener(this);
        table.addMouseListener(new PopupTableListener());
        TablePanel.setJTableColumnsWidth(table, 600, 5);

        setVisible(false);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public static TablePanel getInstance() {
        if (instance == null) {
            instance = new TablePanel();
        }
        return instance;
    }

    public JTable getTable() {
        return instance.table;
    }

    public String getTableName() {
        return this.tableName;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        // String columnName = model.getColumnName(column);
        // Object data = model.getValueAt(row, column);

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

    public void hideTable(String tableName) {
        if (this.tableName.equals(tableName)) {
            setVisible(false);
        }
    }

    class TableModel extends AbstractTableModel {
        private List<String> columnNames = null;
        private List<List<String>> data = null;
        private List<Class> columnClasses;

        public TableModel() {
            this.columnNames = List.of("№");
            this.data = List.of(List.of("1"), List.of("2"));
        }

        public TableModel(List<List<String>> rows, List<String> columns, List<Class> classes) {
            List<String> cn = new ArrayList<>();
            cn.add("№");
            cn.addAll(columns);
            this.columnNames = cn;

            List<Class> cs = new ArrayList<>();
            cs.add(Integer.class);
            cs.addAll(classes);
            this.columnClasses = cs;

            this.data = IntStream.range(0, rows.size())
                        .mapToObj(i -> {
                            List<String> row = new ArrayList<>();
                            row.add(String.valueOf(i + 1));
                            row.addAll(rows.get(i));
                            return row;
                        })
                        .collect(Collectors.toList());

            TablePanel.setJTableColumnsWidth(table, 600, 5);
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
            return columnIndex != 0;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }
            // col - 1 because of number column
            String cellDisplay = DBService.setCellInTable(tableName, row, col - 1, value);
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

    private void setTableName(String name) {
        this.tableName = name;
    }

    public void setTableData(String name) {
        setVisible(true);
        Table t = DBService.getTable(name);
        TableModel tableModel = (TableModel) table
                .getModel();
        this.setTableName(name);
        table.setModel(new TableModel(t.getRows(), t.getColumnsName(), t.getViewTypes()));
        tableModel.fireTableStructureChanged();
    }

    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
                                             double... percentages) {
        int tableColumnsCount = table.getColumnModel().getColumnCount();
        double total = 0;
        double[] newPers = new double[tableColumnsCount];
        if (percentages.length < tableColumnsCount) {
            double setPers = 0;
            for (int i = 0; i < percentages.length; i++) {
                newPers[i] = percentages[i];
                setPers += percentages[i];
            }
            double remainPers = (100 - setPers) / (tableColumnsCount - percentages.length);
            for (int i = percentages.length; i < tableColumnsCount; i++) {
                newPers[i] = remainPers;
            }
        }
        for (int i = 0; i < tableColumnsCount; i++) {
            total += newPers[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (newPers[i] / total)));
        }
    }

}
