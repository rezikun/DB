package form.popupMenus.tree;

import entities.DataBase;
import form.DataBaseTree;
import form.TablePanel;
import service.DBService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextTableMenu extends JPopupMenu implements ActionListener {
    JMenuItem editTableItem;
    JMenuItem deleteTableItem;
    String databaseName;
    String tableName;
    public ContextTableMenu() {
        editTableItem = new JMenuItem("Rename table");
        editTableItem.addActionListener(this);
        editTableItem.setActionCommand("renameTable");
        add(editTableItem);

        deleteTableItem = new JMenuItem("Delete");
        deleteTableItem.addActionListener(this);
        deleteTableItem.setActionCommand("deleteTable");
        add(deleteTableItem);
    }

    public void setTableName(String name) {
        this.tableName = name;
        this.databaseName = DBService.getCurrentDBName();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("editTable".equals(e.getActionCommand())) {
            // CreateTableFrame frame = new CreateTableFrame(databaseName);
        }
        if ("deleteTable".equals(e.getActionCommand())) {
            DBService.deleteTable(tableName);
            TablePanel.getInstance().hideTable(tableName);
            DataBaseTree.getInstance().revalidateAfterTableDeletion();
        }
        if("renameTable".equals(e.getActionCommand())) {
            this.showRenameTableDialog(tableName);
        }
    }

    private void showRenameTableDialog(String tableName) {
        String s = (String)JOptionPane.showInputDialog(
                this.getParent(),
                "Write new table name",
                "Edit table name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                tableName);
        if ((s != null) && (s.length() > 0)) {
            DBService.renameTable(tableName, s);
        }
        DataBaseTree.getInstance().revalidateAfterTableNameEdit(s);
    }
}
