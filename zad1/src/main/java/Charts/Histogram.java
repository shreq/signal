package Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.math.BigDecimal;
import java.util.Map;

public class Histogram extends ApplicationFrame {

    public Histogram(String appTitle, String chartTitle, Map<BigDecimal, Double> map) {
        super(appTitle);
        JFreeChart chart = ChartFactory.createHistogram(
                chartTitle, "value", "frequency",
                createDataset(map),
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private HistogramDataset createDataset(Map<BigDecimal, Double> map) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("aaa", map.values().stream().mapToDouble(Double::doubleValue).toArray(), 20);
        return dataset;
    }
}
