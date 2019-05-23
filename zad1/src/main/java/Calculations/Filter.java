package Calculations;

import Calculations.Windows.Window;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Filter {

    public static TreeMap<BigDecimal, Double> lowpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp, Window window) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        double k = fp / f0;
        int mid = (m - 1) / 2;

        for (int i = 0; i < m; i++) {
            result.put(keys[i], filterResponse(i, mid, k) * window.calculate(i, m));
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

    public static TreeMap<BigDecimal, Double> midpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp, Window window) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        Double[] lowpass = lowpass(signal, m, f0, fp, window).values().toArray(new Double[0]);

        for (int i = 0; i < lowpass.length; i++) {
            result.put(keys[i], 2 * lowpass[i] * Math.sin(Math.PI * i / 2.0));
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> highpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp, Window window) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        Double[] lowpass = lowpass(signal, m, f0, fp, window).values().toArray(new Double[0]);

        for (int i = 0; i < lowpass.length; i++) {
            result.put(keys[i], lowpass[i] * (i % 2 == 0 ? 1 : -1));
        }

        return result;
    }

    private static double filterResponse(int i, int mid, double k) {
        if (i == mid) {
            return 2 / k;
        }

        return Math.sin((2 * Math.PI * (i - mid)) / k) / (Math.PI * (i - mid));
    }
}
