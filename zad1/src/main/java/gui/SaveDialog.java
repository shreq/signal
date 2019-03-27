package gui;

import serialization.Serialization;
import serialization.SerializationModel;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.TreeMap;

public class SaveDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel fsLabel;
    private JTextField fsTextField;
    private JTextField filenameTextField;
    private JLabel filenameLabel;
    private JButton browseButton;
    private JLabel frequencyLabel;

    private TreeMap<BigDecimal, Double> data;
    private double fs;

    public SaveDialog(TreeMap<BigDecimal, Double> _data, double _fs) {
        fs = _fs;
        data = _data;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        frequencyLabel.setText(frequencyLabel.getText() + fs);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
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

        browseButton.addActionListener(e -> {
            onBrowse();
        });
    }

    private void onOK() {
        double sample = Double.parseDouble(fsTextField.getText());
        if(sample > fs || sample <= 0){
            JOptionPane.showMessageDialog(null, "Invalid sampling frequency");
            return;
        }
        SerializationModel model = new SerializationModel(data.firstKey().doubleValue(), sample, data);
        try {
            Serialization.Serialize(model, filenameTextField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onBrowse(){
        final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        int returnVal = fc.showOpenDialog(contentPane);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            filenameTextField.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    public static void showDialog(TreeMap<BigDecimal, Double> _data, double _fs) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SaveDialog dialog = new SaveDialog(_data, _fs);
        dialog.pack();
        dialog.setVisible(true);
    }
}
