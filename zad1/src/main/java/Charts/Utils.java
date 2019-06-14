package Charts;

import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Utils {

    public static int BINS = 20;

    public static XYDataset createDatasetSignal(Map<BigDecimal, Double> map) {
        XYSeries series = new XYSeries("");
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (Map.Entry<BigDecimal, Double> entry : map.entrySet()) {
            series.add(new BigDecimal(((Map.Entry) entry).getKey().toString()), (double) ((Map.Entry) entry).getValue());
        }

        dataset.addSeries(series);
        return dataset;
    }

    public static XYDataset createDatasetSignal(ArrayList<Complex> signal) {
        XYSeries series1 = new XYSeries("series1");
        XYSeries series2 = new XYSeries("series2");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < signal.size(); i++) {

            series1.add(i, signal.get(i).getReal());
            series2.add(i, signal.get(i).getImaginary());
        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    public static XYDataset createDatasetSignal2(ArrayList<Complex> signal) {
        XYSeries series1 = new XYSeries("series1");
        XYSeries series2 = new XYSeries("series2");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < signal.size(); i++) {

            series1.add(i, signal.get(i).abs());
            series2.add(i, signal.get(i).getArgument());
        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    public static HistogramDataset createDatasetHistogram(Map<BigDecimal, Double> map, int bins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("aaa", map.values().stream().mapToDouble(Double::doubleValue).toArray(), bins);
        return dataset;
    }

    public static void showChart(JFreeChart chart){
        JFrame chartFrame = new JFrame();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }

    public static void drawSignal(String name, TreeMap<BigDecimal, Double> map) {
        XYDataset dataset = Utils.createDatasetSignal(map);
        JFreeChart chart;

        if (name.equals("ImpulseUnit") || name.equals("NoiseImpulse")) {
            chart = ChartFactory.createScatterPlot(
                    name, "t[s]", "A",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );
        }
        else {
            chart = ChartFactory.createXYLineChart(
                    name, "t[s]", "A",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );
        }

        showChart(chart);
    }

    public static void drawHistogram(String name, TreeMap<BigDecimal, Double> map) {

        HistogramDataset dataset = Utils.createDatasetHistogram(map, BINS);
        if (map.size() > 0) {
            JFreeChart chart = ChartFactory.createHistogram(
                    name, "value", "frequency",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false
            );
            showChart(chart);
        }
    }

    public static void drawComplex(String name, ArrayList<Complex> signal){

        XYDataset dataset = createDatasetSignal(signal);
        JFreeChart chart= ChartFactory.createXYLineChart(
                name, "", "", dataset,  PlotOrientation.VERTICAL,
                true, true, false
        );
        showChart(chart);
    }

    public static void drawComplex2(String name, ArrayList<Complex> signal){

        XYDataset dataset = createDatasetSignal2(signal);
        JFreeChart chart= ChartFactory.createXYLineChart(
                name, "", "", dataset,  PlotOrientation.VERTICAL,
                true, true, false
        );
        showChart(chart);
    }
}
