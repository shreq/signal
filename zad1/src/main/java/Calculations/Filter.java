package Calculations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Filter {

    public static TreeMap<BigDecimal, Double> lowpass(TreeMap<BigDecimal, Double> signal, double smoothing) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        Double value = 0.0;

        for (Map.Entry<BigDecimal, Double> entry : signal.entrySet()) {
            value += (entry.getValue() - value) / smoothing;
            result.put(entry.getKey(), value);
        }

        return result;
    }
}
