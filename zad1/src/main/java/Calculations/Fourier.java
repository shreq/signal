package Calculations;

import java.math.BigDecimal;
import java.util.TreeMap;

public class Fourier {

    private static TreeMap<BigDecimal, Double> discreteFourierTransform(TreeMap<BigDecimal, Double> signal) {
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        Double[] values = signal.values().toArray(new Double[0]);

        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        for (int i = 0; i < keys.length; i++) {
            double sumValue = 0.0;
            double sumKey = 0.0;

            for (int j = 0; j < keys.length; j++) {
                double asd = 2 * Math.PI * j * i / keys.length;

                sumValue += values[j] * Math.cos(asd) + keys[j].doubleValue() * Math.sin(asd);
                sumKey += -values[j] * Math.sin(asd) + keys[j].doubleValue() * Math.cos(asd);
            }

            result.put(new BigDecimal(sumKey), sumValue);
        }

        return result;
    }
}
