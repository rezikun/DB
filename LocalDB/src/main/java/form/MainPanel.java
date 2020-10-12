package form;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.Enumeration;

public class MainPanel extends JPanel {

    private JTree tree;
    private TablePane table;

    public MainPanel() {
        tree = DataBaseTree.getInstance();

        JScrollPane treeView = new JScrollPane(tree);

        table = TablePane.getInstance();

        JScrollPane tableView = new JScrollPane(table);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(tableView);

        Dimension minimumSize = new Dimension(200, 500);
        tableView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(800, 500));

        //Add the split pane to this panel.
        setOpaque(true);
        add(splitPane);
    }
}
