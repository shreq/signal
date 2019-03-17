import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Operator {

    public static TreeMap<BigDecimal, Double> Addition(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>(a);

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            result.put(e.getKey(), e.getValue() + (d == null ? 0 : d));
        }

        return result;
    }
}
