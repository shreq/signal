package gui;

import Charts.DrawerXYLineChart;
import Signals.*;
import org.jfree.chart.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.Vector;

public class App implements ItemListener {

    private void draw(Signal signal){
        TreeMap<BigDecimal, Double> map1 = signal.generate();
        DrawerXYLineChart d = new DrawerXYLineChart("Chart", signal.getClass().getSimpleName(), map1);
        d.pack();
        UIUtils.centerFrameOnScreen(d);
        d.setVisible(true);
    }

    private JPanel mainPanel;

    JPanel cards; //a panel that uses CardLayout
    final static String SIGNAL1 = "NoiseUniformDistribution";
    final static String SIGNAL2 = "NoiseGaussian";
    final static String SIGNAL3 = "SignalSinusoidal";
    final static String SIGNAL4 = "SignalSinusoidalStraightenedOneHalf";
    final static String SIGNAL5 = "SignalSinusoidalStraightenedTwoHalf";
    final static String SIGNAL6 = "SignalRectangular";
    final static String SIGNAL7 = "SignalRectangularSymmetric";
    final static String SIGNAL8 = "SignalTriangular";
    final static String SIGNAL9 = "StepUnit";
    final static String SIGNAL10 = "NoiseImpulse";

    private JPanel createCard(Class<? extends Signal> signalClass, String... names){
        JPanel card = new JPanel();
        JTextField[] fields = new JTextField[names.length];
        for (int i = 0; i<names.length; i++) {
            fields[i] = new JTextField(names[i], 4);
            card.add(fields[i]);
        }
        JButton button = new JButton("Show");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] params = new double[names.length];
                for(int i = 0; i<names.length; i++){
                    params[i] = Double.parseDouble(fields[i].getText());
                }
                Signal signal = null;
                try {
                    signal = signalClass.newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                signal.setAllFields(params);
                draw(signal);
            }
        });
        card.add(button);
        return card;
    }

    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { SIGNAL1, SIGNAL2, SIGNAL3, SIGNAL4, SIGNAL5,
                SIGNAL6, SIGNAL7, SIGNAL8, SIGNAL9, SIGNAL10};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        Vector<JPanel> cardsArray = new Vector<>();
        //Create the "cards".
        //NoiseUniformDistribution
        cardsArray.add(
                createCard(NoiseUniformDistribution.class, "A", "t1", "d"));
        //NoiseGaussian
        cardsArray.add(
                createCard(NoiseGaussian.class, "t1", "d"));
        //SignalSinusoidal
        cardsArray.add(
                createCard(SignalSinusoidal.class, "A", "t1", "d", "T"));
        //SignalSinusoidalStraightenedOneHalf
        cardsArray.add(
                createCard(SignalSinusoidalStraightenedOneHalf.class, "A", "t1", "d", "T"));
        //SignalSinusoidalStraightenedTwoHalf
        cardsArray.add(
                createCard(SignalSinusoidalStraightenedTwoHalf.class, "A", "t1", "d", "T"));
        //SignalRectangular
        cardsArray.add(
                createCard(SignalRectangular.class, "A", "t1", "d", "T", "kw"));
        //SignalRectangularSymmetric
        cardsArray.add(
                createCard(SignalRectangularSymmetric.class, "A", "t1", "d", "T", "kw"));
        //SignalTriangular
        cardsArray.add(
                createCard(SignalTriangular.class, "A", "t1", "d", "T", "kw"));
        //StepUnit
        cardsArray.add(
                createCard(StepUnit.class, "A", "t1", "d", "ts"));
        //NoiseImpulse
        cardsArray.add(
                createCard(NoiseImpulse.class, "A", "t1", "d", "f", "p"));

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        for(int i = 0; i<cardsArray.size(); i++){
            cards.add(cardsArray.elementAt(i), comboBoxItems[i]);
        }
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        App demo = new App();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setSize(500, 250);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
