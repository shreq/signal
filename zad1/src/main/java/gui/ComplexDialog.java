package gui;

import Calculations.Wavelet;
import Charts.Utils;
import org.apache.commons.math3.complex.Complex;
import serialization.Serialization;
import serialization.SerializationModel;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;

public class ComplexDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField loadTextField;
    private JButton browse;
    private JButton chart1Button;
    private JButton chart2Button;
    private JButton backwardWaveletButton;

    public ComplexDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        browse.addActionListener(e-> onBrowse());

        chart1Button.addActionListener(e->{
            ArrayList<Complex> complex = new ArrayList<>();
            try {
                complex = (ArrayList<Complex>) Serialization.Deserialize(loadTextField.getText());
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }
            Utils.drawComplex("Complex signal", complex);
        });

        chart2Button.addActionListener(e->{
            //todo
        });

        backwardWaveletButton.addActionListener(e->{
            ArrayList<Complex> complex = new ArrayList<>();
            try {
                complex = (ArrayList<Complex>) Serialization.Deserialize(loadTextField.getText());
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }
            TreeMap<BigDecimal, Double> result = Wavelet.backward(complex);
            Utils.drawSignal("Backward wavelet transform", result);
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
        // add your code here
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
            loadTextField.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    public static void showDialog() {
        ComplexDialog dialog = new ComplexDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
