package form.popupMenus.table;

import form.TablePanel;
import service.DBService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupTableListener extends MouseAdapter {
    ContextRowsMenu tablePanelMenu;
    ContextRowsMenu tableCellsMenu;
    ContextRowsMenu fileCellMenu;

    public PopupTableListener(){
        this.tablePanelMenu = new ContextRowsMenu();

        this.tableCellsMenu = new ContextRowsMenu();
        this.tableCellsMenu.setDeleteRowItem();
        this.tableCellsMenu.setSortByColumnItem();
        // this.tableCellsMenu.setEditColumnNameItem();

        this.fileCellMenu = new ContextRowsMenu();
        this.fileCellMenu.setDeleteRowItem();
        this.fileCellMenu.setSortByColumnItem();
        this.fileCellMenu.setFileUploadItem();
    }

    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)) {
            int row = TablePanel.getInstance().getTable().getSelectedRow();
            int column = TablePanel.getInstance().getTable().getSelectedColumn();
            if (DBService.isFileColumn(TablePanel.getInstance().getTableName(), column - 1)) {
                showFileCellPopup(e);
            }
            else if (row > -1) {
                showTableCellsPopup(e);
            }
            else {
                showTablePanelPopup(e);
            }
        }
    }

    private void showTablePanelPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            tablePanelMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }

    private void showTableCellsPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            tableCellsMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }

    private void showFileCellPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            fileCellMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}
