package Calculations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Quantizer {

    public static TreeMap<BigDecimal, Double> quantize(TreeMap<BigDecimal, Double> signal, int resolution){
        int levels = (int)Math.pow(2.0, resolution);
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        double levelSize = (Collections.max(signal.values()) - Collections.min(signal.values()))/levels;
        for(Map.Entry<BigDecimal, Double> e : signal.entrySet()){
            int temp = (int)(e.getValue()/levelSize);
            if(temp == levels)
                --temp;
            result.put(e.getKey(), temp*levelSize);
        }
        return result;
    }
}
