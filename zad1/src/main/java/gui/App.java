package gui;

import Calculations.Calculator;
import Charts.DrawerXYLineChart;
import Charts.Histogram;
import Signals.*;
import org.jfree.chart.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.Vector;

public class App implements ItemListener {

    final int BINS = 20;

    private void drawSignal(Signal signal) {
        TreeMap<BigDecimal, Double> map = signal.generate();
        DrawerXYLineChart d = new DrawerXYLineChart("Chart", signal.getClass().getSimpleName(), map);
        d.pack();
        UIUtils.centerFrameOnScreen(d);
        d.setVisible(true);
    }

    private void drawHistogram(Signal signal) {
        TreeMap<BigDecimal, Double> map = signal.generate();

        // region trimming map
        if (signal instanceof SignalRectangular) {
            map = Calculator.Trim((SignalRectangular) signal, map);
        }
        else if (signal instanceof SignalRectangularSymmetric) {
            map = Calculator.Trim((SignalRectangularSymmetric) signal, map);
        }
        else if (signal instanceof SignalSinusoidal) {
            map = Calculator.Trim((SignalSinusoidal) signal, map);
        }
        else if (signal instanceof SignalSinusoidalStraightenedOneHalf) {
            map = Calculator.Trim((SignalSinusoidalStraightenedOneHalf) signal, map);
        }
        else if (signal instanceof SignalSinusoidalStraightenedTwoHalf) {
            map = Calculator.Trim((SignalSinusoidalStraightenedTwoHalf) signal, map);
        }
        else if (signal instanceof SignalTriangular) {
            map = Calculator.Trim((SignalTriangular) signal, map);
        }
        // endregion

        if (map.size() > 0) {
            Histogram e = new Histogram("Chart", "Histogram", map, BINS);
            e.pack();
            UIUtils.centerFrameOnScreen(e);
            e.setVisible(true);
        }
    }

    private JPanel mainPanel;

    private JPanel cards;  //a panel that uses CardLayout
    private final static String SIGNAL1 = "NoiseUniformDistribution";
    private final static String SIGNAL2 = "NoiseGaussian";
    private final static String SIGNAL3 = "SignalSinusoidal";
    private final static String SIGNAL4 = "SignalSinusoidalStraightenedOneHalf";
    private final static String SIGNAL5 = "SignalSinusoidalStraightenedTwoHalf";
    private final static String SIGNAL6 = "SignalRectangular";
    private final static String SIGNAL7 = "SignalRectangularSymmetric";
    private final static String SIGNAL8 = "SignalTriangular";
    private final static String SIGNAL9 = "StepUnit";
    private final static String SIGNAL10 = "NoiseImpulse";

    private JPanel createCard(Class<? extends Signal> signalClass, String... names) {
        JPanel card = new JPanel();
        JTextField[] fields = new JTextField[names.length];

        for (int i = 0; i < names.length; i++) {
            fields[i] = new JTextField(names[i], 4);
            card.add(fields[i]);
        }

        JButton button = new JButton("Show");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] params = new double[names.length];
                for(int i = 0; i < names.length; i++) {
                    params[i] = Double.parseDouble(fields[i].getText());
                }
                Signal signal = null;
                try {
                    signal = signalClass.newInstance();
                }
                catch (InstantiationException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                signal.setAllFields(params);
                drawSignal(signal);
                drawHistogram(signal);
            }
        });

        card.add(button);
        return card;
    }

    public void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = {SIGNAL1, SIGNAL2, SIGNAL3, SIGNAL4, SIGNAL5, SIGNAL6, SIGNAL7, SIGNAL8, SIGNAL9, SIGNAL10};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        Vector<JPanel> cardsArray = new Vector<>();

        // region create the cards
        // NoiseUniformDistribution
        cardsArray.add(createCard(NoiseUniformDistribution.class, "A", "t1", "d"));

        // NoiseGaussian
        cardsArray.add(createCard(NoiseGaussian.class, "t1", "d"));

        // SignalSinusoidal
        cardsArray.add(createCard(SignalSinusoidal.class, "A", "t1", "d", "T"));

        // SignalSinusoidalStraightenedOneHalf
        cardsArray.add(createCard(SignalSinusoidalStraightenedOneHalf.class, "A", "t1", "d", "T"));

        // SignalSinusoidalStraightenedTwoHalf
        cardsArray.add(createCard(SignalSinusoidalStraightenedTwoHalf.class, "A", "t1", "d", "T"));

        // SignalRectangular
        cardsArray.add(createCard(SignalRectangular.class, "A", "t1", "d", "T", "kw"));

        // SignalRectangularSymmetric
        cardsArray.add(createCard(SignalRectangularSymmetric.class, "A", "t1", "d", "T", "kw"));

        // SignalTriangular
        cardsArray.add(createCard(SignalTriangular.class, "A", "t1", "d", "T", "kw"));

        // StepUnit
        cardsArray.add(createCard(StepUnit.class, "A", "t1", "d", "ts"));

        // NoiseImpulse
        cardsArray.add(createCard(NoiseImpulse.class, "A", "t1", "d", "f", "p"));
        // endregion

        // create the panel that contains the cards
        cards = new JPanel(new CardLayout());
        for(int i = 0; i < cardsArray.size(); i++) {
            cards.add(cardsArray.elementAt(i), comboBoxItems[i]);
        }
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    private static void createAndShowGUI() {
        // create and set up the window
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create and set up the content pane
        App demo = new App();
        demo.addComponentToPane(frame.getContentPane());

        // display the window
        frame.pack();
        frame.setSize(500, 250);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IllegalAccessException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        UIManager.put("swing.boldMetal", Boolean.FALSE);

        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
