package form.popupMenus.tree;

import form.CreateTableFrame;
import form.DataBaseTree;
import service.DBService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextDatabaseMenu extends JPopupMenu implements ActionListener {
    JMenuItem addTableItem;
    JMenuItem deleteDBItem;
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
    }
}
