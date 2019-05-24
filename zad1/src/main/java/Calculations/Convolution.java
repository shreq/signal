package Calculations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

public class Convolution {

    public static TreeMap<BigDecimal, Double> convolve(TreeMap<BigDecimal, Double> signalA, TreeMap<BigDecimal, Double> signalB) {
        Double[] valuesA = signalA.values().toArray(new Double[0]);
        Double[] valuesB = reverse(signalB).values().toArray(new Double[0]);

        /*Double[] result = new Double[valuesA.length + valuesB.length - 1];
        Arrays.fill(result, 0.0);
        for (int i = 0; i < valuesA.length; i++) {
            for (int j = 0; j < valuesB.length; j++) {
                result[i + j] += valuesA[i] * valuesB[j];
            }
        }*/

        Double[] vec = new Double[valuesA.length + valuesB.length - 1];
        Arrays.fill(vec, 0.0);
        for (int i = 0; i < valuesA.length; i++) {
            vec[i + (int) Math.ceil(valuesB.length / 2)] = valuesA[i];
        }

        Double[] result = new Double[vec.length];
        int end = 0;
        while (end < vec.length) {
            double sum = 0.0;
            for (int i = 0; i < valuesB.length; i++) {
                sum += valuesB[i] * vec[end + i];
            }
            result[end] = sum;
            end++;
        }

        return join(signalA.keySet().toArray(new BigDecimal[0]), result);
    }

    static TreeMap<BigDecimal, Double> join(BigDecimal[] keys, Double[] values) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        for (int i = 0; i < keys.length; i++) {
            result.put(new BigDecimal(i), values[i]);
        }

        return result;
    }

    static TreeMap<BigDecimal, Double> reverse(TreeMap<BigDecimal, Double> map) {
        TreeMap<BigDecimal, Double> reversed = new TreeMap<>(Collections.reverseOrder());
        reversed.putAll(map);
        return reversed;
    }
}
