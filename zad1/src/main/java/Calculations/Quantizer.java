package Calculations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Quantizer {

    public static TreeMap<BigDecimal, Double> quantize(TreeMap<BigDecimal, Double> signal, int resolution){
        int levels = (int)Math.pow(2.0, resolution);
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        double max = Collections.max(signal.values());
        double min = Collections.min(signal.values());
        double levelSize = (max - min)/levels;
        for(Map.Entry<BigDecimal, Double> e : signal.entrySet()){
            int temp = (int)Math.round(e.getValue()/levelSize);
            result.put(e.getKey(), temp*levelSize);
        }
        return result;
    }
}
