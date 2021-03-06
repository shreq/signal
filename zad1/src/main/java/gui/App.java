package gui;

import Calculations.*;
import Charts.Utils;
import Signals.*;
import org.apache.commons.math3.complex.Complex;
import serialization.Serialization;
import serialization.SerializationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

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
    private final static String SIGNAL11 = "ImpulseUnit";
    private final static String SIGNAL12 = "S2";

    private TreeMap<BigDecimal, Double> currentData;
    private double currentFs;
    private String currentName;

    private JPanel createCard(Class<? extends Signal> signalClass, String... names) {

        // region create main GroupLayout groups
        JPanel card = new JPanel();
        GroupLayout layout = new GroupLayout(card);
        GroupLayout.SequentialGroup mainVertical = layout.createSequentialGroup();
        GroupLayout.ParallelGroup mainHorizontal = layout.createParallelGroup();
        prepareGroupPanel(card, mainHorizontal, mainVertical, layout);
        // endregion

        // region create input fields
        GroupLayout.ParallelGroup inputFieldsVertical = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        GroupLayout.SequentialGroup inputFieldsHorizontal = layout.createSequentialGroup();
        JTextField[] fields = new JTextField[names.length];
        for (int i = 0; i < names.length; i++) {
            fields[i] = new JTextField();
            JLabel nameLabel = new JLabel(names[i]);
            GroupLayout.ParallelGroup pairHoriz = layout.createParallelGroup();
            GroupLayout.SequentialGroup pairVert = layout.createSequentialGroup();
            pairHoriz.addComponent(nameLabel);
            pairHoriz.addComponent(fields[i]);
            pairVert.addComponent(nameLabel);
            pairVert.addComponent(fields[i]);
            inputFieldsVertical.addGroup(pairVert);
            inputFieldsHorizontal.addGroup(pairHoriz);
        }
        JLabel fsLabel = new JLabel("fs");
        JTextField fsTextField = new JTextField();
        GroupLayout.ParallelGroup pairHoriz = layout.createParallelGroup();
        GroupLayout.SequentialGroup pairVert = layout.createSequentialGroup();
        pairHoriz.addComponent(fsLabel);
        pairHoriz.addComponent(fsTextField);
        pairVert.addComponent(fsLabel);
        pairVert.addComponent(fsTextField);
        inputFieldsVertical.addGroup(pairVert);
        inputFieldsHorizontal.addGroup(pairHoriz);
        mainVertical.addGroup(inputFieldsVertical);
        mainHorizontal.addGroup(inputFieldsHorizontal);
        // endregion
        // region create Show button
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
            currentName = signalClass.getSimpleName();
        });
        // endregion
        return card;
    }

    private void addComponentToPane(Container pane) {

        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = {SIGNAL1, SIGNAL2, SIGNAL3, SIGNAL4, SIGNAL5, SIGNAL6, SIGNAL7, SIGNAL8, SIGNAL9, SIGNAL10, SIGNAL11, SIGNAL12};
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
        cardsArray.add(createCard(NoiseImpulse.class, "A", "t1", "d", "p"));
        // ImpulseUnit
        cardsArray.add(createCard(ImpulseUnit.class, "A", "ns", "n1", "d"));
        // S2
        cardsArray.add(createCard(Signal2.class, "t1", "d"));
        // endregion

        // create the panel that contains the cards
        cards = new JPanel(new CardLayout());
        // pair cards with their names
        for(int i = 0; i < cardsArray.size(); i++) {
            cards.add(cardsArray.elementAt(i), comboBoxItems[i]);
        }

        // region create save and math buttons
        JPanel mainBottomPane = new JPanel();
        GroupLayout layout = new GroupLayout(mainBottomPane);
        GroupLayout.SequentialGroup mainVert = layout.createSequentialGroup();
        GroupLayout.ParallelGroup mainHoriz = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        prepareGroupPanel(mainBottomPane, mainHoriz, mainVert, layout);

        JButton save = new JButton("Save");
        JButton load = new JButton("Load");
        JButton saveText = new JButton("Save Text");
        JButton selectButton = new JButton("Select signals");
        JButton add = new JButton("Add");
        JButton subtract = new JButton("Subtract");
        JButton multiply = new JButton("Multiply");
        JButton divide = new JButton("Divide");
        JButton quantize = new JButton("Quantize");
        JButton sincRec = new JButton("Sinc Rec.");
        JButton zeroHold = new JButton("ZOH");
        JLabel binsLabel = new JLabel("Histogram bins: ");
        JTextField binsTextField = new JTextField("20", 3);
        JButton correlation = new JButton("Correlation");
        JButton convolution = new JButton("Convolution");
        JButton filter = new JButton("Filter");
        JButton antena = new JButton("Antena");
        JButton kurier1 = new JButton("DFT");
        JButton kurier2 = new JButton("FFT");
        JButton wavelet = new JButton("Wavelet");
        JButton complex = new JButton("Complex");

        save.addActionListener(e -> SaveDialog.showDialog(currentData, currentFs, currentName));
        load.addActionListener(e -> {
            LoadDialog dialog = new LoadDialog();
            dialog.pack();
            dialog.setVisible(true);
            setModelAsCurrent(dialog.getModel());
        });
        saveText.addActionListener(e -> saveToTextFile());

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
            setModelAsCurrent(resultModel);
        });
        subtract.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Subtraction(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " - " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " - " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " - " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " minus " + data.get(1).name).replace(" ", "_"));
            setModelAsCurrent(resultModel);
        });
        multiply.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Multiplication(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " * " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " * " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " * " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " multiply " + data.get(1).name).replace(" ", "_"));
            setModelAsCurrent(resultModel);
        });
        divide.addActionListener(e -> {
            TreeMap<BigDecimal, Double> result = Operator.Division(data.get(0).data, data.get(1).data);
            Utils.drawSignal(data.get(0).name + " / " + data.get(1).name, result);
            Utils.drawHistogram(data.get(0).name + " / " + data.get(1).name, result);
            SerializationModel resultModel = new SerializationModel(result.firstKey().doubleValue(), data.get(0).fs,
                    result, (data.get(0).name + " / " + data.get(1).name));
            saveOperationOutput(resultModel, (data.get(0).name + " divide " + data.get(1).name).replace(" ", "_"));
            setModelAsCurrent(resultModel);
        });

        quantize.addActionListener(e-> QuantizeDialog.showDialog(currentData, currentFs, currentName));
        sincRec.addActionListener(e-> SincRecDialog.showDialog(currentData, currentFs));
        zeroHold.addActionListener(e-> ZeroOrderHoldDialog.showDialog(currentData, currentFs, currentName));
        binsTextField.addActionListener(e -> Utils.BINS = Integer.parseInt(binsTextField.getText()));
        correlation.addActionListener(e-> {
            TreeMap<BigDecimal, Double> result = Correlation.correlate(data.get(0).data, data.get(1).data);
            Utils.drawSignal("Correlation of " + data.get(0).name + " " + data.get(1).name, result);
            Utils.drawHistogram("Correlation of " + data.get(0).name + " " + data.get(1).name, result);
        });
        convolution.addActionListener(e->{
            TreeMap<BigDecimal, Double> result = Convolution.convolve(data.get(0).data, data.get(1).data);
            Utils.drawSignal("Convolution of " + data.get(0).name + " " + data.get(1).name, result);
            Utils.drawHistogram("Convolution of " + data.get(0).name + " " + data.get(1).name, result);
        });
        filter.addActionListener(e-> {
            FilterDialog dialog = new FilterDialog(currentData);
            dialog.pack();
            dialog.setVisible(true);
        });
        antena.addActionListener(e->{
            AntenaDialog dialog = new AntenaDialog();
            dialog.pack();
            dialog.setVisible(true);
        });
        kurier1.addActionListener(e->{
            ArrayList<Complex> result = Fourier.discreteFourierTransform(currentData);
            JOptionPane.showMessageDialog(null, "Time: " + Fourier.timeDFT + "ms");
            try {
                Serialization.Serialize(result, "DFT");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        kurier2.addActionListener(e->{
            long startTime = System.currentTimeMillis();
            ArrayList<Complex> result = Fourier.fastFourierTransform(currentData);
            Fourier.timeFFT = System.currentTimeMillis() - startTime;
            result.removeAll(Collections.singleton(null));
            JOptionPane.showMessageDialog(null, "Time: " + Fourier.timeFFT + "ms");
            try {
                Serialization.Serialize(result, "FFT");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        wavelet.addActionListener(e->{
            ArrayList<Complex> result = Wavelet.transfrom(currentData);
            try {
                Serialization.Serialize(result, "wavelet");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        complex.addActionListener(e-> ComplexDialog.showDialog());

        GroupLayout.SequentialGroup firstRowHoriz = layout.createSequentialGroup();
        GroupLayout.ParallelGroup firstRowVert = layout.createParallelGroup();
        firstRowHoriz.addComponent(selectButton);   firstRowVert.addComponent(selectButton);
        firstRowHoriz.addComponent(add);            firstRowVert.addComponent(add);
        firstRowHoriz.addComponent(subtract);       firstRowVert.addComponent(subtract);
        firstRowHoriz.addComponent(multiply);       firstRowVert.addComponent(multiply);
        firstRowHoriz.addComponent(divide);         firstRowVert.addComponent(divide);
        firstRowHoriz.addComponent(binsLabel);      firstRowVert.addComponent(binsLabel);
        firstRowHoriz.addComponent(binsTextField);  firstRowVert.addComponent(binsTextField);

        GroupLayout.SequentialGroup secondRowHoriz = layout.createSequentialGroup();
        GroupLayout.ParallelGroup secondRowVert = layout.createParallelGroup();
        secondRowHoriz.addComponent(quantize);      secondRowVert.addComponent(quantize);
        secondRowHoriz.addComponent(sincRec);       secondRowVert.addComponent(sincRec);
        secondRowHoriz.addComponent(zeroHold);      secondRowVert.addComponent(zeroHold);

        GroupLayout.SequentialGroup thirdRowHoriz = layout.createSequentialGroup();
        GroupLayout.ParallelGroup thirdRowVert = layout.createParallelGroup();
        thirdRowHoriz.addComponent(saveText);       thirdRowVert.addComponent(saveText);
        thirdRowHoriz.addComponent(save);           thirdRowVert.addComponent(save);
        thirdRowHoriz.addComponent(load);           thirdRowVert.addComponent(load);

        GroupLayout.SequentialGroup fourthRowHoriz = layout.createSequentialGroup();
        GroupLayout.ParallelGroup fourthRowVert = layout.createParallelGroup();
        fourthRowHoriz.addComponent(correlation);   fourthRowVert.addComponent(correlation);
        fourthRowHoriz.addComponent(convolution);   fourthRowVert.addComponent(convolution);
        fourthRowHoriz.addComponent(filter);        fourthRowVert.addComponent(filter);
        fourthRowHoriz.addComponent(antena);        fourthRowVert.addComponent(antena);

        GroupLayout.SequentialGroup fifthRowHoriz = layout.createSequentialGroup();
        GroupLayout.ParallelGroup fifthRowVert = layout.createParallelGroup();
        fifthRowHoriz.addComponent(kurier1);   fifthRowVert.addComponent(kurier1);
        fifthRowHoriz.addComponent(kurier2);   fifthRowVert.addComponent(kurier2);
        fifthRowHoriz.addComponent(wavelet);   fifthRowVert.addComponent(wavelet);
        fifthRowHoriz.addComponent(complex);   fifthRowVert.addComponent(complex);

        mainHoriz.addGroup(firstRowHoriz);
        mainHoriz.addGroup(secondRowHoriz);
        mainHoriz.addGroup(thirdRowHoriz);
        mainHoriz.addGroup(fourthRowHoriz);
        mainHoriz.addGroup(fifthRowHoriz);
        mainVert.addGroup(firstRowVert);
        mainVert.addGroup(secondRowVert);
        mainVert.addGroup(thirdRowVert);
        mainVert.addGroup(fourthRowVert);
        mainVert.addGroup(fifthRowVert);
        // endregion

        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(mainBottomPane, BorderLayout.PAGE_END);
    }

    private void prepareGroupPanel(JPanel panel, GroupLayout.Group horizontal, GroupLayout.Group vertical, GroupLayout layout){
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);
    }

    private void saveToTextFile(){
        if(currentData == null) return;
        try (PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "/" + currentName + ".txt")) {
            String output = "";
            out.println("Signal name: " + currentName);
            out.println("Sampling frequecy: " + currentFs + "\n\n");
            out.println("Data: " + "\nX\tY");
            for (Map.Entry<BigDecimal, Double> entry : currentData.entrySet()) {
                if(entry.getValue() == null) continue;
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setModelAsCurrent(SerializationModel model){
        if(model == null) return;
        currentName = model.name;
        currentFs = model.fs;
        currentData = model.data;
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
        frame.setSize(550, 325);
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
