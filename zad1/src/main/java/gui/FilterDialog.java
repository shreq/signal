package gui;

import Calculations.Convolution;
import Calculations.Filter;
import Calculations.Windows.RectangularWindow;
import Charts.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.TreeMap;

public class FilterDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField mField;
    private JTextField f0Field;
    private JTextField fpField;
    private TreeMap<BigDecimal, Double> signal;

    public FilterDialog(TreeMap<BigDecimal, Double> signal) {
        this.signal = signal;
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
        TreeMap<BigDecimal, Double> result = Filter.lowpass(signal, Integer.parseInt(mField.getText()),
                Double.parseDouble(f0Field.getText()),
                Double.parseDouble(fpField.getText()),
                new RectangularWindow());
        TreeMap<BigDecimal, Double> siema = Convolution.convolve(result, signal);
        Utils.drawSignal("Filtr", siema);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
