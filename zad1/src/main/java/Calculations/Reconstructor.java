package Calculations;

import java.math.BigDecimal;
import java.util.TreeMap;

public interface Reconstructor {

    TreeMap<BigDecimal, Double> reconstruct(TreeMap<BigDecimal, Double> signal, int radius);
}
