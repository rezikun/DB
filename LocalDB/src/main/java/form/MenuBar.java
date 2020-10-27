package form;

import service.DBService;

import javax.swing.*;
import java.awt.event.*;

public class MenuBar implements ActionListener, ItemListener {
    private JFrame mainFrame;
    private JMenuBar menuBar;

    private JMenuItem saveMenuItem;
    private JMenuItem createMenuItem;

    public MenuBar(JFrame frame) {
        mainFrame = frame;
        menuBar = new JMenuBar();
    }

    public JMenuBar createMenuBar() {
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Build the first menu.
        menu = new JMenu("File");
        menu.getAccessibleContext().setAccessibleDescription(
                "Main program menu");
        menuBar.add(menu);

        //a group of JMenuItems
        createMenuItem = new JMenuItem("Create new database");
        createMenuItem.getAccessibleContext().setAccessibleDescription(
                "Creates new database");
        createMenuItem.addActionListener(this);
        menu.add(createMenuItem);

        saveMenuItem = new JMenuItem("Save database");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.getAccessibleContext().setAccessibleDescription(
                "Creates new database");
        saveMenuItem.addActionListener(this);
        menu.add(saveMenuItem);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        if (source == saveMenuItem) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Database was saved");
        }
        if (source == createMenuItem) {
            showCreateDBDialog();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                + "\n"
                + "    Event source: " + source.getText()
                + " (an instance of " + getClassName(source) + ")"
                + "\n"
                + "    New state: "
                + ((e.getStateChange() == ItemEvent.SELECTED) ?
                "selected":"unselected");
        JOptionPane.showMessageDialog(mainFrame,
                s);
    }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    public void showCreateDBDialog() {
        String s = (String)JOptionPane.showInputDialog(
                mainFrame,
                "Enter name for database",
                "Create database",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                DBService.getNewUniqueName());
        if ((s != null) && (s.length() > 0)) {
            DBService.createDB(s);
        }
        DataBaseTree.getInstance().revalidateTree();
    }
}
