package gui;

import Calculations.Calculator;
import Calculations.Operator;
import Charts.Utils;
import Signals.*;
import serialization.Serialization;
import serialization.SerializationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

public class App implements ItemListener {

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

    private TreeMap<BigDecimal, Double> currentData;
    private double currentFs;
    private String curretName;

    private JPanel createCard(Class<? extends Signal> signalClass, String... names) {
        JPanel card = new JPanel();
        GroupLayout layout = new GroupLayout(card);
        card.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // region create main GroupLayout groups
        GroupLayout.SequentialGroup mainVertical = layout.createSequentialGroup();
        layout.setVerticalGroup(mainVertical);
        GroupLayout.ParallelGroup mainHorizontal = layout.createParallelGroup();
        layout.setHorizontalGroup(mainHorizontal);
        // endregion

        // region create input fields
        JLabel label = new JLabel("Required fields: " + String.join(", ", names) + ", fs");
        mainVertical.addComponent(label);
        mainHorizontal.addComponent(label, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup inputFieldsVertical = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        GroupLayout.SequentialGroup inputFieldsHorizontal = layout.createSequentialGroup();
        JTextField[] fields = new JTextField[names.length];
        for (int i = 0; i < names.length; i++) {
            fields[i] = new JTextField(names[i], 4);
            inputFieldsVertical.addComponent(fields[i]);
            inputFieldsHorizontal.addComponent(fields[i]);
        }
        JTextField fsTextField = new JTextField("fs");
        inputFieldsVertical.addComponent(fsTextField);
        inputFieldsHorizontal.addComponent(fsTextField);
        mainVertical.addGroup(inputFieldsVertical);
        mainHorizontal.addGroup(inputFieldsHorizontal);
        // endregion
        // region create button
        JButton button = new JButton("Show");
        mainVertical.addComponent(button);
        mainHorizontal.addComponent(button, GroupLayout.Alignment.CENTER);
        // endregion
        // region create output fields
        JLabel mean = new JLabel();
        JLabel meanDesc = new JLabel("Mean:");
        JLabel meanAbs = new JLabel();
        JLabel meanAbsDesc = new JLabel("Abs. mean:");
        JLabel rootMeanSqr = new JLabel();
        JLabel rootMeanSqrDesc = new JLabel("Root mean sqr.:");
        JLabel variance = new JLabel();
        JLabel varianceDesc = new JLabel("Variance:");
        JLabel avgPower = new JLabel();
        JLabel avgPowerDesc = new JLabel("Avg. power:");
        GroupLayout.ParallelGroup outputFieldsVertical = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        GroupLayout.SequentialGroup outputFieldsHorizontal = layout.createSequentialGroup();
        outputFieldsVertical.addComponent(meanDesc).addComponent(mean).addComponent(meanAbsDesc)
                .addComponent(meanAbs).addComponent(rootMeanSqrDesc).addComponent(rootMeanSqr)
                .addComponent(varianceDesc).addComponent(variance).addComponent(avgPowerDesc)
                .addComponent(avgPower);
        outputFieldsHorizontal.addComponent(meanDesc).addComponent(mean).addComponent(meanAbsDesc)
                .addComponent(meanAbs).addComponent(rootMeanSqrDesc).addComponent(rootMeanSqr)
                .addComponent(varianceDesc).addComponent(variance).addComponent(avgPowerDesc)
                .addComponent(avgPower);
        mainVertical.addGroup(outputFieldsVertical);
        mainHorizontal.addGroup(outputFieldsHorizontal);
        // endregion
        // region create signal
        Signal signal = null;
        try {
            signal = signalClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        Signal finalSignal = signal;
        // endregion
        // region create button action listener
        button.addActionListener(e -> {
            double[] params = new double[names.length];
            double d = -1, T = -1;
            for(int i = 0; i < names.length; i++) {
                params[i] = Double.parseDouble(fields[i].getText());
                if(names[i].equals("d")) d = params[i];
                if(names[i].equals("T")) T = params[i];
            }

            assert finalSignal != null;
            finalSignal.setAllFields(params);
            TreeMap<BigDecimal, Double> data = finalSignal.generate(Double.parseDouble(fsTextField.getText()));

            Utils.drawSignal(signalClass.getSimpleName(), data);
            if( d != -1 && T != -1 ){
                data = Calculator.Trim(new BigDecimal(d), new BigDecimal(T), data);
            }
            Utils.drawHistogram(signalClass.getSimpleName(), data);
            DecimalFormat dc = new DecimalFormat("0.00000");
            dc.setRoundingMode(RoundingMode.CEILING);
            mean.setText(dc.format(Calculator.Mean(data)));
            meanAbs.setText(dc.format(Calculator.MeanAbsolute(data)));
            rootMeanSqr.setText(dc.format(Calculator.RootMeanSquare(data)));
            variance.setText(dc.format(Calculator.Variance(data)));
            avgPower.setText(dc.format(Calculator.AvgPower(data)));
            currentFs = Double.parseDouble(fsTextField.getText());
            currentData = data;
            curretName = signalClass.getSimpleName();
        });
        // endregion
        return card;
    }

    private void addComponentToPane(Container pane) {

        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = {SIGNAL1, SIGNAL2, SIGNAL3, SIGNAL4, SIGNAL5, SIGNAL6, SIGNAL7, SIGNAL8, SIGNAL9, SIGNAL10};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        // region create the cards
        Vector<JPanel> cardsArray = new Vector<>();
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
        // pair cards with their names
        for(int i = 0; i < cardsArray.size(); i++) {
            cards.add(cardsArray.elementAt(i), comboBoxItems[i]);
        }

        // region create save and math buttons
        JPanel mainBottomPane = new JPanel();
        JPanel buttonPane1 = new JPanel();
        JPanel buttonPane2 = new JPanel();
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");
        save.addActionListener(e -> SaveDialog.showDialog(currentData, currentFs, curretName));
        load.addActionListener(e -> LoadDialog.showDialog());
        buttonPane1.add(save);
        buttonPane1.add(load);
        JButton selectButton = new JButton("Select signals");
        JButton add = new JButton("Add");
        JButton subtract = new JButton("Subtract");
        JButton multiply = new JButton("Multiply");
        JButton divide = new JButton("Divide");
        ArrayList<SerializationModel> data = new ArrayList<>();
        data.add(null);
        data.add(null);
        selectButton.addActionListener(e -> {
            ArrayList<SerializationModel> temp = getDataFromFiles(pane);
            data.set(0, temp.get(0));
            data.set(1, temp.get(1));
        });
        add.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Addition(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " + " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " + " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " + " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " plus " + data.get(1).name).replace(" ", "_"));
        });
        subtract.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Subtraction(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " - " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " - " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " - " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " minus " + data.get(1).name).replace(" ", "_"));
        });
        multiply.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Multiplication(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " * " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " * " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " * " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " multiply " + data.get(1).name).replace(" ", "_"));
        });
        divide.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Division(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " / " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " / " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " / " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " divide " + data.get(1).name).replace(" ", "_"));
        });
        buttonPane2.add(selectButton);
        buttonPane2.add(add);
        buttonPane2.add(subtract);
        buttonPane2.add(multiply);
        buttonPane2.add(divide);
        mainBottomPane.add(buttonPane1, BorderLayout.PAGE_START);
        mainBottomPane.add(buttonPane2, BorderLayout.PAGE_END);
        // endregion
        mainBottomPane.add(new JLabel("Histogram bins: "));
        JTextField binsTextField = new JTextField("", 3);
        binsTextField.addActionListener(e -> {
            Utils.BINS = Integer.parseInt(binsTextField.getText());
        });
        mainBottomPane.add(binsTextField);

        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(mainBottomPane, BorderLayout.PAGE_END);
    }

    private void saveOperationOutput(SerializationModel model, String fileName){
        try {
            Serialization.Serialize(model, System.getProperty("user.dir") + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<SerializationModel> getDataFromFiles(Container pane){
        ArrayList<SerializationModel> ret = new ArrayList<>();
        final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        int returnVal = fc.showOpenDialog(pane);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try {
                ret.add((SerializationModel)Serialization.Deserialize(fc.getSelectedFile().getAbsolutePath()));
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
        returnVal = fc.showOpenDialog(pane);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try {
                ret.add((SerializationModel)Serialization.Deserialize(fc.getSelectedFile().getAbsolutePath()));
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    private static void createAndShowGUI() {
        // create and set up the window
        JFrame frame = new JFrame("CPS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create and set up the content pane
        App demo = new App();
        demo.addComponentToPane(frame.getContentPane());

        // display the window
        frame.pack();
        frame.setSize(700, 220);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IllegalAccessException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        UIManager.put("swing.boldMetal", Boolean.FALSE);

        javax.swing.SwingUtilities.invokeLater(App::createAndShowGUI);
    }
}
