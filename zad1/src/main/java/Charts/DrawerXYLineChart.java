package Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.math.BigDecimal;
import java.util.Map;

public class DrawerXYLineChart extends ApplicationFrame {

    public JFreeChart chart;
    public ChartPanel chartPanel;

    public DrawerXYLineChart(String appTitle, String chartTitle, Map<BigDecimal, Double> map) {
        super(appTitle);
        chart = ChartFactory.createXYLineChart(
                chartTitle, "t[s]", "A",
                createDataset(map),
                PlotOrientation.VERTICAL,
                true, true, false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset(Map<BigDecimal, Double> map) {
        XYSeries series = new XYSeries("");
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (Map.Entry<BigDecimal, Double> entry : map.entrySet()) {
            Map.Entry pair = entry;
            series.add(new BigDecimal(pair.getKey().toString()), (double) pair.getValue());
        }

        dataset.addSeries(series);
        return dataset;
    }
}
