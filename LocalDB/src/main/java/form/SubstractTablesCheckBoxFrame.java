package form;

import entities.Table;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import service.DBService;

public class SubstractTablesCheckBoxFrame extends JFrame
    implements ItemListener, ActionListener {

  List<String> choices;

  public SubstractTablesCheckBoxFrame(String databaseName) {

    setTitle("Subtract tables");

    setBounds(600, 200, 200, 100);
    setDefaultCloseOperation(HIDE_ON_CLOSE);
    setLayout(new BorderLayout());

    List<String> tables = DBService.getTablesByDB(databaseName);
    JPanel checkPanel = new JPanel(new GridLayout(0, 1));

    //Create the check boxes.
    for(String table: tables) {
      JCheckBox button = new JCheckBox(table);
      button.setActionCommand(table);
      button.addItemListener(this);
      checkPanel.add(button);
    }
    //Indicates what's on the geek.
    choices = new ArrayList<>();

    //Put the check boxes in a column in a panel

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton saveButton = new JButton("Subtract");
    saveButton.setActionCommand("subtract");
    saveButton.addActionListener(this);
    buttonPanel.add(saveButton);

    add(buttonPanel, BorderLayout.SOUTH);
    add(checkPanel, BorderLayout.LINE_START);

    setVisible(true);
  }

  /** Listens to the check boxes. */
  public void itemStateChanged(ItemEvent e) {
    JCheckBox item = (JCheckBox)e.getItem();

    if (e.getStateChange() == ItemEvent.SELECTED) {
      choices.add(item.getActionCommand());
    }
    if (e.getStateChange() == ItemEvent.DESELECTED) {
      choices.remove(item.getActionCommand());
    }
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if ("subtract".equals(actionEvent.getActionCommand())){
        Table table = DBService.subtract(choices);
        loadTable(table);
        this.dispose();
    }
  }

  private void loadTable(Table table) {
    TablePanel.getInstance().setSubtractTable(table);
  }
}
