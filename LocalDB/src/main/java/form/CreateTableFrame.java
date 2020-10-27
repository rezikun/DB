package form;

import entities.Column;
import service.DBService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CreateTableFrame extends JFrame implements ActionListener {
    private JTextField tableName;
    private JPanel main;
    private JPanel columnsPanel;
    private String databaseName;

    private String[] types = {"string", "int", "real", "char", "file", "interval"};

    public CreateTableFrame(String databaseName) {
        this.databaseName = databaseName;

        setTitle("Create table in " + databaseName);
        setBounds(300, 90, 450, 400);
        setMinimumSize(new Dimension(450, 300));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        main = new JPanel();

        JLabel label = new JLabel("Enter name");
        main.add(label);

        tableName = new JTextField(30);
        tableName.addActionListener(this);
        tableName.setActionCommand("enterTableName");
        tableName.setText(DBService.getNewUniqueTableName(databaseName));
        main.add(tableName);

        JLabel nameLabel = new JLabel("Columns: ");
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        main.add(nameLabel);

        columnsPanel = new JPanel();
        columnsPanel.setLayout(new BoxLayout(columnsPanel, BoxLayout.Y_AXIS));
        addTableFields();
        main.add(columnsPanel);

        JButton addTableButton = new JButton("Add column");
        addTableButton.setVerticalTextPosition(AbstractButton.CENTER);
        addTableButton.setHorizontalTextPosition(AbstractButton.LEADING);
        addTableButton.setActionCommand("addColumn");
        addTableButton.addActionListener(this);
        main.add(addTableButton);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(main);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSize(new Dimension(450, getHeight()));
                super.componentResized(e);
            }
        });
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println(e.getActionCommand());
        if ("addColumn".equals(e.getActionCommand())){
            addTableFields();
        }
        if("save".equals(e.getActionCommand())) {
            saveTable();
        }
        if("cancel".equals(e.getActionCommand())) {
            this.dispose();
        }
    }

    private void addTableFields() {
        JPanel columnPanel = new JPanel();

        JTextField columnNameField = new JTextField(20);
        columnNameField.addActionListener(this);
        columnNameField.setActionCommand("enterColumnName");
        columnPanel.add(columnNameField);

        JComboBox<String> typesList = new JComboBox<>(types);
        typesList.setSelectedIndex(0);
        typesList.addActionListener(this);
        columnPanel.add(typesList);

        columnPanel.setAlignmentX(CENTER_ALIGNMENT);
        columnsPanel.add(columnPanel);

        columnsPanel.revalidate();
    }

    private void saveTable() {
        var columnsPanelComponents = columnsPanel.getComponents();
        List<Column> columns = new ArrayList<>();
        for (var column : columnsPanelComponents) {
            var columnPanel = (JPanel) column;
            Column newColumn = new Column();
            for (var item : columnPanel.getComponents()) {
                if (item.getClass().equals(JTextField.class)) {
                    var itemName = (JTextField) item;
                    if (itemName.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(main,
                                "Enter all fields",
                                "Creation error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        newColumn.setName(itemName.getText());
                    }
                }
                if (item.getClass().equals(JComboBox.class)) {
                    var itemType = (JComboBox<String>) item;
                    newColumn.setType(String.valueOf(itemType.getSelectedItem()));
                }
            }
            columns.add(newColumn);
        }
        DBService.getDBByName(databaseName);
        DBService.createTable(tableName.getText(), columns);
        DataBaseTree.getInstance().revalidateAfterTableCreation();
        this.dispose();
    }
}
