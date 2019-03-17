import Signals.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

import static Signals.Signal.SCALE;

public class Calculator {

    //region Trimmers
    public static TreeMap<BigDecimal, Double> Trim(SignalRectangular signal, TreeMap<BigDecimal, Double> map) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        int b = (int) Math.floor(signal.d.doubleValue() / signal.T.doubleValue());
        BigDecimal t = signal.T.multiply(new BigDecimal(b));
        for (Map.Entry<BigDecimal, Double> e : map.entrySet()) {

            if (e.getKey().compareTo(t) < 0) {
                result.put(e.getKey(), e.getValue());
            }
        }

        return result;
    }
    //endregion
}
