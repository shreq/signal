package gui;

import Calculations.Calculator;
import Calculations.Quantizer;
import Charts.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.TreeMap;

public class QuantizeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField resolutionField;
    private JLabel resolutionLabel;
    private TreeMap<BigDecimal, Double> data;

    public QuantizeDialog(TreeMap<BigDecimal, Double> data) {
        this.data = data;
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
        int resolution = Integer.parseInt(resolutionField.getText());
        TreeMap<BigDecimal, Double> result = Quantizer.quantize(data, resolution);
        DecimalFormat dc = new DecimalFormat("0.00000");
        Utils.drawSignal("Quanitzation MSE: " + dc.format(Calculator.MeanSquareError(data, result)) +
                " SNR: " + dc.format(Calculator.MeanSquareError(data, result)) +
                " PSNR: " + dc.format(Calculator.MeanSquareError(data, result)) +
                " MD: " + dc.format(Calculator.MeanSquareError(data, result)), result);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void showDialog(TreeMap<BigDecimal, Double> _data) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        QuantizeDialog dialog = new QuantizeDialog(_data);
        dialog.pack();
        dialog.setVisible(true);
    }
}
