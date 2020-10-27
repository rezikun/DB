package form;

import service.DBService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileChooserFrame extends JFrame
        implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
    private JPanel main;
    private Integer rowIndex;
    private Integer columnIndex;

    public FileChooserFrame(Integer row, Integer col) {
        this.rowIndex = row;
        this.columnIndex = col;

        setTitle("Choose txt file");
        setBounds(300, 90, 450, 400);
        setMinimumSize(new Dimension(450, 300));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        main = new JPanel();

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Load a File...");
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        //Add the buttons and the log to this panel.
        main.add(logScrollPane, BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.PAGE_END);

        add(main);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooserFrame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                log.append(file.getName() + " was chosen" + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

            //Handle save button action.
        } else if (e.getSource() == saveButton) {
            // int returnVal = fc.showSaveDialog(FileChooserFrame.this);
            // if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                try{
                    DBService.storeFileType(TablePanel.getInstance().getTableName(), columnIndex, rowIndex, file);
                    TablePanel.getInstance().setTableData(TablePanel.getInstance().getTableName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(),
                            "File upload error",
                            JOptionPane.ERROR_MESSAGE);
                }
            this.dispose();
        }
    }
}
