package form.popupMenus.table;

import form.FileChooserFrame;
import form.TablePanel;
import service.DBService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextRowsMenu extends JPopupMenu implements ActionListener {

    public ContextRowsMenu() {
        JMenuItem addRowItem = new JMenuItem("Add row");
        addRowItem.addActionListener(this);
        addRowItem.setActionCommand("addRow");
        add(addRowItem);
    }

    public void setEditColumnNameItem(){
        JMenuItem item = new JMenuItem("Edit column");
        item.addActionListener(this);
        item.setActionCommand("editColumn");
        add(item);
    }

    public void setSortByColumnItem() {
        JMenuItem item = new JMenuItem("Sort by this column");
        item.addActionListener(this);
        item.setActionCommand("sort");
        add(item);
    }

    public void setDeleteRowItem() {
        JMenuItem item = new JMenuItem("Delete row");
        item.addActionListener(this);
        item.setActionCommand("deleteRow");
        add(item);
    }

    public void setFileUploadItem() {
        JMenuItem item = new JMenuItem("Change file");
        item.addActionListener(this);
        item.setActionCommand("fileUpload");
        add(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("addRow".equals(e.getActionCommand())) {
            DBService.addEmptyRow(TablePanel.getInstance().getTableName());
            TablePanel.getInstance().setTableData(TablePanel.getInstance().getTableName());
        }
        if("deleteRow".equals(e.getActionCommand())) {
            int row = TablePanel.getInstance().getTable().getSelectedRow();
            DBService.deleteRow(TablePanel.getInstance().getTableName(), row);
            TablePanel.getInstance().setTableData(TablePanel.getInstance().getTableName());
        }
        if("sort".equals(e.getActionCommand())) {
            int column = TablePanel.getInstance().getTable().getSelectedColumn();
            DBService.sortByColumn(TablePanel.getInstance().getTableName(), column - 1);
            TablePanel.getInstance().setTableData(TablePanel.getInstance().getTableName());
        }
        if("editColumn".equals(e.getActionCommand())) {
            int column = TablePanel.getInstance().getTable().getSelectedColumn();
            this.showEditColumnNameDialog(column - 1);
        }
        if("fileUpload".equals(e.getActionCommand())) {
            int row = TablePanel.getInstance().getTable().getSelectedRow();
            int column = TablePanel.getInstance().getTable().getSelectedColumn();
            FileChooserFrame f = new FileChooserFrame(row, column - 1);
        }
    }

    public void showEditColumnNameDialog(Integer columnIndex) {
        String s = (String)JOptionPane.showInputDialog(
                this.getRootPane(),
                "Edit column name",
                "Edit",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                String.valueOf(columnIndex));
        if ((s != null) && (s.length() > 0)) {
            // DBService.createDB(s);
        }
        // DataBaseTree.getInstance().revalidateTree();
    }
}
