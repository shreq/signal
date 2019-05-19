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

    private static double filterResponse(int n, int k) {
        if (n == 0) {
            return 2 / k;
        }

        return Math.sin((2 * Math.PI * n) / k) / (Math.PI * n);
    }

    private static double hamming(int n, int m) {
        return 0.53836 - 0.46164 * Math.cos((2 * Math.PI * n) / m);
    }

    private static double hanning(int n, int m) {
        return 0.5 - 0.5 * Math.cos((2 * Math.PI * n) / m);
    }

    private static double blackman(int n, int m) {
        return 0.42 - 0.5 * Math.cos((2 * Math.PI * n) / m) + 0.08 * Math.cos((4 * Math.PI * n) / m);
    }
}
