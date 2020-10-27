package form;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private JTree tree;
    private TablePanel table;

    public MainPanel() {
        tree = DataBaseTree.getTreeInstance();

        JScrollPane treeView = new JScrollPane(tree);

        table = TablePanel.getInstance();

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
