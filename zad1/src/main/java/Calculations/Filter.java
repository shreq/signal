package Calculations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Filter {

    public static TreeMap<BigDecimal, Double> lowpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        double k = fp / f0;
        int mid = (m - 1) / 2;

        for (int i = 1; i <= m; i++) {
            result.put(keys[i], filterResponse(i, mid, k));
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> lowpass0(TreeMap<BigDecimal, Double> signal, double smoothing) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        Double value = 0.0;

        for (Map.Entry<BigDecimal, Double> entry : signal.entrySet()) {
            value += (entry.getValue() - value) / smoothing;
            result.put(entry.getKey(), value);
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> midpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        TreeMap<BigDecimal, Double> lowpass = lowpass(signal, m, f0, fp);

        return result;
    }

    private static double filterResponse(int i, int mid, double k) {
        if (i == mid) {
            return 2 / k;
        }

        return Math.sin((2 * Math.PI * i) / k) / (Math.PI * i);
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
