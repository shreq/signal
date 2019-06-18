package Calculations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.TreeMap;

public class Correlation {

    public static TreeMap<BigDecimal, Double> correlate(TreeMap<BigDecimal, Double> signalA, TreeMap<BigDecimal, Double> signalB) {
        return Convolution.convolve(signalA, reverse(signalB));
    }

     private static TreeMap<BigDecimal, Double> reverse(TreeMap<BigDecimal, Double> map) {
        TreeMap<BigDecimal, Double> reversed = new TreeMap<>(Collections.reverseOrder());
        reversed.putAll(map);
        return reversed;
    }
}
