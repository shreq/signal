package Charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Drawer {

    public void draw(HashMap<Float, Float> map) {
        XYSeries series = new XYSeries("");

        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            series.add((float)pair.getKey(), (float)pair.getValue());
            it.remove();
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);


        JFreeChart chart = ChartFactory.createXYLineChart("a", "x", "y", dataset);
        XYPlot plot = chart.getXYPlot();
    }
}
