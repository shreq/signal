package Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.math.BigDecimal;
import java.util.Map;

public class DrawerLineChart extends ApplicationFrame {

    public DrawerLineChart(String appTitle, String chartTitle, Map<BigDecimal, Double> map) {
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

    private DefaultCategoryDataset createDataset(Map<BigDecimal, Double> map) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<BigDecimal, Double> entry : map.entrySet()) {
            Map.Entry pair = entry;
            dataset.addValue(new BigDecimal(pair.getValue().toString()), "signal", pair.getKey().toString());
        }

        return dataset;
    }
}
