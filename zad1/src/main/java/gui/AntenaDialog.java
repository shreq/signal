package gui;

import Calculations.Antenna;
import Calculations.Operator;
import Signals.NoiseGaussian;
import Signals.SignalSinusoidal;

import javax.swing.*;
import java.awt.event.*;

public class AntenaDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField signalVelocityField;
    private JTextField objectVelocityField;
    private JTextField startingDistanceField;
    private JTextField refreshPeriodField;
    private JLabel distanceLabel;
    private Antenna antenna;

    public AntenaDialog() {
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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        double velocity = Double.parseDouble(signalVelocityField.getText());
        double objectVelocity = Double.parseDouble(objectVelocityField.getText());
        double startingDistance = Double.parseDouble(startingDistanceField.getText());
        long refreshPeriod = Long.parseLong(refreshPeriodField.getText());

        //antenna = new Antenna(new SignalSinusoidal(1, 0, 10, 1).generate(100), velocity, refreshPeriod, objectVelocity, true, startingDistance);
        antenna = new Antenna(Operator.Addition(new SignalSinusoidal(1, 0, 10, 5).generate(100), new SignalSinusoidal(1, 0, 10, 2).generate(100)), velocity, refreshPeriod, objectVelocity, true, startingDistance);

        Thread thread = new Thread(() -> {
            try {
                antenna.simulate(distanceLabel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void onCancel() {
        antenna.active = false;
    }

    public static void main(String[] args) {
        AntenaDialog dialog = new AntenaDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
