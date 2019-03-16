package Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class Drawer extends ApplicationFrame {

    public Drawer(String appTitle, String chartTitle, Map<Double, Double> map) {
        super(appTitle);
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle, "t[s]", "A",
                createDataset(map),
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(Map<Double, Double> map) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<Double, Double> doubleDoubleEntry : map.entrySet()) {
            Map.Entry pair = doubleDoubleEntry;
            dataset.addValue((double) pair.getValue(), "signal", pair.getKey().toString());
        }

        return dataset;
    }
}
