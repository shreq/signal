package gui;

import Charts.Utils;
import serialization.Serialization;
import serialization.SerializationModel;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoadDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel loadLabel;
    private JTextField loadTextField;
    private JButton browseButton;

    public LoadDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        browseButton.addActionListener(e -> {
            onBrowse();
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        SerializationModel model = null;
        try {
            model = (SerializationModel)Serialization.Deserialize(loadTextField.getText());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispose();
        Utils.drawSignal("name", model.data);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onBrowse(){
        final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        int returnVal = fc.showOpenDialog(contentPane);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            loadTextField.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    public static void showDialog() {
        LoadDialog dialog = new LoadDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
