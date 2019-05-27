package Calculations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Convolution {

    public static TreeMap<BigDecimal, Double> convolve(TreeMap<BigDecimal, Double> signalA, TreeMap<BigDecimal, Double> signalB) {
        Double[] valuesA = signalA.values().toArray(new Double[0]);
        Double[] valuesB = signalB.values().toArray(new Double[0]);
        BigDecimal[] keys = signalA.keySet().toArray(new BigDecimal[0]);

        int samples = valuesA.length + valuesB.length - 1;
        BigDecimal T = keys[1].subtract(keys[0]);
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < samples; i++) {
            int left = 0;
            int right = i;
            double val = 0.0;

            while (left < valuesA.length && right >= 0) {
                if (right < valuesB.length) {
                    val += valuesA[left] * valuesB[right];
                }
                left++;
                right--;
            }
            
            result.add(val);
        }

        return join(result, keys[0], T);
    }

    private static TreeMap<BigDecimal, Double> join(List<Double> values, BigDecimal t0, BigDecimal T) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        BigDecimal tx = t0;
        for (Double value : values) {
            result.put(tx, value);
            tx = tx.add(T);
        }

        return result;
    }
}
