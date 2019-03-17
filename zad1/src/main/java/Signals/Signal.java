package Signals;

import java.math.BigDecimal;
import java.util.TreeMap;

public interface Signal {
    short SAMPLES = 500;
    int SCALE = 5;

    TreeMap<BigDecimal, Double> generate(BigDecimal fs);
    TreeMap<BigDecimal, Double> generate(double fs);
    TreeMap<BigDecimal, Double> generate();
}
