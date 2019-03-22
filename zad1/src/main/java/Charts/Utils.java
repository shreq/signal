package Charts;

import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.math.BigDecimal;
import java.util.Map;

public class Utils {

    public static XYDataset createDatasetSignal(Map<BigDecimal, Double> map) {
        XYSeries series = new XYSeries("");
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (Map.Entry<BigDecimal, Double> entry : map.entrySet()) {
            Map.Entry pair = entry;
            series.add(new BigDecimal(pair.getKey().toString()), (double) pair.getValue());
        }

        dataset.addSeries(series);
        return dataset;
    }

    public static HistogramDataset createDatasetHistogram(Map<BigDecimal, Double> map, int bins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("aaa", map.values().stream().mapToDouble(Double::doubleValue).toArray(), bins);
        return dataset;
    }
}
