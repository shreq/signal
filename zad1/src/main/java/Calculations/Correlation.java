package Calculations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.TreeMap;

public class Correlation {

    public static TreeMap<BigDecimal, Double> correlate(TreeMap<BigDecimal, Double> signalA, TreeMap<BigDecimal, Double> signalB) {
        TreeMap<BigDecimal, Double> reversedB = new TreeMap<>(Collections.reverseOrder());
        reversedB.putAll(signalB);

        int i = 0;

        return Convolution.convolve(signalA, reversedB);
    }
}
