package form;

import javax.swing.*;
import java.awt.event.*;

public class MyFrame extends JFrame implements ActionListener {

    public MyFrame() {
        setTitle("Database system");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        MenuBar menu = new MenuBar(this);
        setJMenuBar(menu.createMenuBar());

        add(new MainPanel());
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
