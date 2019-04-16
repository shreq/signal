package gui;

import Calculations.ZeroHoldReconstructor;
import Charts.Utils;

import javax.naming.spi.DirObjectFactory;
import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.TreeMap;

public class ZeroOrderHoldDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField durationField;
    private JLabel singalTLabel;
    private TreeMap<BigDecimal, Double> data;
    private double signalfs;
    private String name;

    public ZeroOrderHoldDialog(TreeMap<BigDecimal, Double> _data, double _fs, String _name) {
        this.data = _data;
        this.signalfs = _fs;
        this.name = _name;
        singalTLabel.setText(singalTLabel.getText() + " " + signalfs);
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
        ZeroHoldReconstructor reconstructor = new ZeroHoldReconstructor();
        TreeMap<BigDecimal, Double> result = reconstructor.reconstruct(data, signalfs,  Double.parseDouble(durationField.getText()));
        Utils.drawSignal("ZOH " + name, result);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void showDialog(TreeMap<BigDecimal, Double> _data, double _fs, String _name){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ZeroOrderHoldDialog dialog = new ZeroOrderHoldDialog(_data, _fs, _name);
        dialog.pack();
        dialog.setVisible(true);
    }
}
