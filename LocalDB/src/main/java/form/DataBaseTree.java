package form;

import entities.DataBase;
import service.DBService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class DataBaseTree implements TreeSelectionListener {

    private static DataBaseTree instance;
    private JTree tree;

    private DataBaseTree() {
        tree = new JTree(createNodes());

        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
    }

    public static JTree getInstance() {
        if (instance == null) {
            instance = new DataBaseTree();
        }
        return instance.tree;
    }

    private DefaultMutableTreeNode createNodes() {
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("DataBases");
        DefaultMutableTreeNode db = null;

        var names = DBService.getAllDataBasesNames();
        for (var name : names) {
            db = new DefaultMutableTreeNode(name);
            top.add(db);
        }

        return top;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null)
            //Nothing is selected.
            return;

        if (node.isLeaf() && node.getLevel() == 1) {
            loadNodes(node);
            SwingUtilities.updateComponentTreeUI(tree.getParent());
        }

        if (node.isLeaf() && node.getLevel() == 2) {
            loadTable(node);
        }
    }

    private void loadNodes(DefaultMutableTreeNode rootNode) {
        Object nodeInfo = rootNode.getUserObject();

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        var nodes = DBService.getTablesByDB(nodeInfo.toString());
        for (var node : nodes) {
            model.insertNodeInto(new DefaultMutableTreeNode(node), rootNode, rootNode.getChildCount());
        }
    }

    private void loadTable(DefaultMutableTreeNode rootNode) {
        Object nodeInfo = rootNode.getUserObject();
        TablePane.getInstance().setTableData(nodeInfo.toString());
    }
}
