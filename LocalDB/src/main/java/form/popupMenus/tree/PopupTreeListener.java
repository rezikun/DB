package form.popupMenus.tree;

import form.DataBaseTree;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupTreeListener extends MouseAdapter {
    ContextDatabaseMenu popupDB;
    ContextTableMenu popupTable;

    public PopupTreeListener() {
        this.popupDB = new ContextDatabaseMenu();
        this.popupTable = new ContextTableMenu();
    }

    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int selRow = DataBaseTree.getTreeInstance().getRowForLocation(e.getX(), e.getY());
            TreePath selPath = DataBaseTree.getTreeInstance().getPathForLocation(e.getX(), e.getY());
            DataBaseTree.getTreeInstance().setSelectionPath(selPath);
            if (selRow > -1) {
                DataBaseTree.getTreeInstance().setSelectionRow(selRow);
                System.out.println(selPath);
            }
            if (selPath != null && selPath.getPathCount() == 2) {
                popupDB.setDatabaseName(selPath.getLastPathComponent().toString());
                showDBPopup(e);
            }
            if (selPath != null && selPath.getPathCount() == 3) {
                popupTable.setTableName(selPath.getLastPathComponent().toString());
                showTablePopup(e);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        showDBPopup(e);
    }

    private void showDBPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupDB.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }

    private void showTablePopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupTable.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}

