package Signals;

import java.math.BigDecimal;
import java.util.TreeMap;

public interface Signal {
    int SAMPLES = 500000;
    int SCALE = 10;

    TreeMap<BigDecimal, Double> generate(BigDecimal fs);
    TreeMap<BigDecimal, Double> generate(double fs);
    TreeMap<BigDecimal, Double> generate();

    public void setAllFields(double... params);
}
