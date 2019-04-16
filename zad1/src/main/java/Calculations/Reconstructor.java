package Calculations;

import java.math.BigDecimal;
import java.util.TreeMap;

public interface Reconstructor {

    double reconstruct(BigDecimal t, TreeMap<BigDecimal, Double> signal);
}
