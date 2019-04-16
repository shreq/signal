package Calculations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class SincReconstructor implements Reconstructor {

    public TreeMap<BigDecimal, Double> reconstruct(TreeMap<BigDecimal, Double> signal) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        BigDecimal[] keys = signal.keySet().toArray(new BigDecimal[0]);
        BigDecimal Ts = keys[1].subtract(keys[0]);

        for (int i = 0; i < signal.values().size(); i++) {
        }

        return result;
    }

    private double sinc(double t) {
        return t == 0.0 ? 1.0 : Math.sin(Math.PI * t) / (Math.PI * t);
    }

    private double sinc(BigDecimal t) {
        return sinc(t.doubleValue());
    }

    @Override
    public double reconstruct(BigDecimal t, TreeMap<BigDecimal, Double> signal) {
        return 0;
    }
}
