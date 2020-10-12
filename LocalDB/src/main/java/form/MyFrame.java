package form;

import entities.DataBase;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JTable table;
    private JTree tree;
    private JEditorPane tablePane;
    private JFrame frame;
    private JPanel mainPanel;

    public MyFrame() {
        setTitle("Database system");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(new MainPanel());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
