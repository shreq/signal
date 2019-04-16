package Calculations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SincReconstructor implements Reconstructor {

    @Override
    public TreeMap<BigDecimal, Double> reconstruct(TreeMap<BigDecimal, Double> signal, int radius) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        Double[] values = signal.values().toArray(new Double[0]);
        BigDecimal Ts = keys[1].subtract(keys[0]);

        for (int i = 0; i < values.length; i++) {
            result.put(keys[i], sum(values[i], values, i, radius));
        }

        return result;
    }

    private double sum(double x, Double[] values, int index, int radius) {
        int left = Math.max(index - radius, 0);
        int right = Math.min(index + radius, values.length - 1);

        double sum = 0.0;
        for (int i = left; i < right; i++) {
            sum += values[i] * sinc(x);
        }
        return sum;
    }

    private double sinc(double t) {
        return t == 0.0 ? 1.0 : Math.sin(Math.PI * t) / (Math.PI * t);
    }

}
