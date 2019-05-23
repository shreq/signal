package gui;

import Calculations.*;
import Calculations.Windows.BlackmanWindow;
import Charts.Utils;
import Signals.*;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.TreeMap;

public class SincRecDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel radiusLabel;
    private JTextField radiusField;
    private TreeMap<BigDecimal, Double> data;
    private double fs;
    private SincReconstructor reconstructor;

    public SincRecDialog(TreeMap<BigDecimal, Double> data, double fs) {
        this.data = data;
        this.fs = fs;
        this.reconstructor = new SincReconstructor();
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
        TreeMap<BigDecimal, Double> result = reconstructor.reconstruct(data, fs, Double.parseDouble(radiusField.getText()));
        /*
        Signal s1 = new SignalRectangular(1, 0, 10, 10, 1);
        Signal s2 = new SignalTriangular(1, 0, 10, 10, 0);
        result = Correlation.correlate(s1.generate(1000), s2.generate(1000));
        */
        /**/
        //Signal s3 = new NoiseUniformDistribution(1, 0, 100);
        Signal s3 = new SignalSinusoidal(1, 0, 10, 10);
        Signal s4 = new SignalSinusoidal(1, 0, 10, 0.1);
        TreeMap<BigDecimal, Double> map = Operator.Addition(s3.generate(50), s4.generate(50));
        TreeMap<BigDecimal, Double> filter = Filter.lowpass(map, 500, 10, 100, new BlackmanWindow());
        result = Convolution.convolve(filter, map);
        /**/
        Utils.drawSignal("Reconstructed signal", result);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void showDialog(TreeMap<BigDecimal, Double> _data, double _fs){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SincRecDialog dialog = new SincRecDialog(_data, _fs);
        dialog.pack();
        dialog.setVisible(true);
    }
}
