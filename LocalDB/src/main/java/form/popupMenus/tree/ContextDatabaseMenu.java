package form.popupMenus.tree;

import form.CreateTableFrame;
import form.DataBaseTree;
import form.SubstractTablesCheckBoxFrame;
import service.DBService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextDatabaseMenu extends JPopupMenu implements ActionListener {
    JMenuItem addTableItem;
    JMenuItem deleteDBItem;
    JMenuItem subtractTables;
    String databaseName;
    public ContextDatabaseMenu() {
        addTableItem = new JMenuItem("Add table");
        addTableItem.addActionListener(this);
        addTableItem.setActionCommand("addTable");
        add(addTableItem);

        deleteDBItem = new JMenuItem("Delete");
        deleteDBItem.addActionListener(this);
        deleteDBItem.setActionCommand("deleteDB");
        add(deleteDBItem);

        subtractTables = new JMenuItem("Subtract tables");
        subtractTables.addActionListener(this);
        subtractTables.setActionCommand("subtract");
        add(subtractTables);
    }

    public void setDatabaseName(String name) {
        this.databaseName = name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if ("addTable".equals(e.getActionCommand())) {
            CreateTableFrame frame = new CreateTableFrame(databaseName);
        }
        if ("deleteDB".equals(e.getActionCommand())) {
            DBService.deleteDB(databaseName);
            DataBaseTree.getInstance().revalidateTree();
        }
        if ("subtract".equals(e.getActionCommand())) {
            SubstractTablesCheckBoxFrame frame = new SubstractTablesCheckBoxFrame(databaseName);
        }
    }

}
