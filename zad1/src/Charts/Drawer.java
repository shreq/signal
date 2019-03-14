package Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Drawer extends ApplicationFrame {

    public Drawer(String appTitle, String chartTitle, HashMap<Double, Double> map) {
        super(appTitle);
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle, "time", "A",
                createDataset(map),
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(HashMap<Double, Double> map) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            dataset.addValue((double)pair.getValue(), "signal", pair.getKey().toString());
        }

        return dataset;
    }
}
