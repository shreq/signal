package Calculations;

import Calculations.Windows.Window;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Filter {

    private static TreeMap<BigDecimal, Double> filter(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp, Window window, double k) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        int mid = (m - 1) / 2;

        for (int i = 0; i < m; i++) {
            result.put(keys[i], filterResponse(i, mid, k) * window.calculate(i, m));
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> lowpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp, Window window) {
        return filter(signal, m, f0, fp, window, fp / f0);
    }

    @Deprecated
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
        TreeMap<BigDecimal, Double> result = filter(signal, m, f0, fp, window, (4 * fp) / (fp - 4 * f0));

        int i = 0;
        for (Map.Entry<BigDecimal, Double> entry : result.entrySet()) {
            result.put(entry.getKey(), entry.getValue() * 2.0 * Math.sin(Math.PI * i / 2.0));
            i++;
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> highpass(TreeMap<BigDecimal, Double> signal, int m, double f0, double fp, Window window) {
        TreeMap<BigDecimal, Double> result = filter(signal, m, f0, fp, window, fp / (fp / 2.0 - f0));

        int i = 0;
        for (Map.Entry<BigDecimal, Double> entry : result.entrySet()) {
            result.put(entry.getKey(), entry.getValue() * (i % 2 == 0 ? 1 : -1));
            i++;
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
