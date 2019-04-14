package Calculations;

import java.math.BigDecimal;
import java.util.*;

public class Quantizer {

    public static TreeMap<BigDecimal, Double> quantize(TreeMap<BigDecimal, Double> signal, int resolution){
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        double min = Collections.min(signal.values());
        double max = Collections.max(signal.values());

        double levels = Math.pow(2.0, resolution) - 1;
        double levelSize = (max - min) / levels;

        List<Double> l = new ArrayList<>(Arrays.asList(min));
        for (int i = 1; i < levels; i++) {
            l.add(l.get(i - 1) + levelSize);
        }
        l.add(max);

        for (Map.Entry<BigDecimal, Double> e : signal.entrySet()) {
            result.put(e.getKey(), getNearestFlooredValue(l, e.getValue()));
        }

        return result;
    }

    private static double getNearestFlooredValue(List<Double> l, double x) {
        int low = 0;
        int high = l.size() - 1;

        if (high < 0)
            throw new IllegalArgumentException("Array is empty");

        while (low < high) {
            int mid = (low + high) / 2;
            double d1 = Math.abs(l.get(mid) - x);
            double d2 = Math.abs(l.get(mid + 1) - x);
            if (d2 <= d1) {
                low = mid + 1;
            }
            else {
                high = mid;
            }
        }

        return x >= l.get(high) ? l.get(high) : l.get(high - 1);
    }
}
