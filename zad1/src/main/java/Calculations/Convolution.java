package Calculations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.TreeMap;

public class Convolution {

    public static TreeMap<BigDecimal, Double> convolute(TreeMap<BigDecimal, Double> signalA, TreeMap<BigDecimal, Double> signalB) {
        Double[] valuesA = signalA.values().toArray(new Double[0]);
        Double[] valuesB = signalB.values().toArray(new Double[0]);

        Double[] vec = new Double[valuesA.length + valuesB.length - 1];
        Arrays.fill(vec, 0.0);
        for (int i = 0; i < valuesA.length; i++) {
            vec[i + (int) Math.ceil(valuesB.length / 2)] = valuesA[i];
        }

        Double[] result = new Double[valuesA.length];
        int end = 0;
        while (end < valuesA.length) {
            double sum = 0.0;
            for (int i = 0; i < valuesB.length; i++) {
                sum += valuesB[i] * vec[end + i];
            }
            result[end] = sum;
            end++;
        }

        return join(signalA.keySet().toArray(new BigDecimal[0]), result);
    }

    private static TreeMap<BigDecimal, Double> join(BigDecimal[] keys, Double[] values) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        for (int i = 0; i < keys.length; i++) {
            result.put(keys[i], values[i]);
        }

        return result;
    }
}
