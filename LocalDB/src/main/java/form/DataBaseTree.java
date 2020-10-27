package form;

import form.popupMenus.tree.PopupTreeListener;
import service.DBService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class DataBaseTree implements TreeSelectionListener {

    private static DataBaseTree instance;
    private JTree tree;

    private DataBaseTree() {
        tree = new JTree(createNodes());

        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        tree.addMouseListener(new PopupTreeListener());
    }

    public static JTree getTreeInstance() {
        if (instance == null) {
            instance = new DataBaseTree();
        }
        return instance.tree;
    }

    public static DataBaseTree getInstance() {
        if (instance == null) {
            instance = new DataBaseTree();
        }
        return instance;
    }

    public void revalidateTree() {
        var root = (DefaultMutableTreeNode)tree.getModel().getRoot();
        if (DBService.getAllDataBasesNames().size() != tree.getModel().getChildCount(root)) {
            updateDataBaseList(root);
        }
    }

    public void revalidateAfterTableCreation() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();
        node.removeAllChildren();
        loadTables(node, false);
        SwingUtilities.updateComponentTreeUI(tree.getParent());
    }

    public void revalidateAfterTableDeletion() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.removeNodeFromParent(node);
    }

    public void revalidateAfterTableNameEdit(String name) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        node.setUserObject(name);
        model.nodeChanged(node);
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

    private void updateDataBaseList(DefaultMutableTreeNode root) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.setRoot(createNodes());
        model.reload(root);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null)
            //Nothing is selected.
            return;

        if (node.isLeaf() && node.getLevel() == 1) {
            loadTables(node, true);
            SwingUtilities.updateComponentTreeUI(tree.getParent());
        }

        if (node.isLeaf() && node.getLevel() == 2) {
            DefaultMutableTreeNode db = (DefaultMutableTreeNode)node.getParent();
            DBService.getDBByName(db.getUserObject().toString());
            loadTable(node);
        }

        if (node.getLevel() == 1) {
            DBService.getDBByName(node.getUserObject().toString());
        }
    }

    private void loadTables(DefaultMutableTreeNode rootNode, boolean showCreateTable) {
        Object nodeInfo = rootNode.getUserObject();

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        var nodes = DBService.getTablesByDB(nodeInfo.toString());
        if (nodes.isEmpty() && showCreateTable) {
            CreateTableFrame frame = new CreateTableFrame(nodeInfo.toString());
            return;
        }
        for (var node : nodes) {
            model.insertNodeInto(new DefaultMutableTreeNode(node), rootNode, rootNode.getChildCount());
        }

    }

    private void loadTable(DefaultMutableTreeNode rootNode) {
        Object nodeInfo = rootNode.getUserObject();
        TablePanel.getInstance().setTableData(nodeInfo.toString());
    }
}
