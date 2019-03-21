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

    public JFreeChart chart;
    public ChartPanel chartPanel;

    public Histogram(String appTitle, String chartTitle, Map<BigDecimal, Double> map, int bins) {
        super(appTitle);
        chart = ChartFactory.createHistogram(
                chartTitle, "value", "frequency",
                createDataset(map, bins),
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(640, 480));
        setContentPane(chartPanel);
    }

    private HistogramDataset createDataset(Map<BigDecimal, Double> map, int bins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("aaa", map.values().stream().mapToDouble(Double::doubleValue).toArray(), bins);
        return dataset;
    }
}
