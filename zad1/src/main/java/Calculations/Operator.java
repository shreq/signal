package Calculations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Operator {

    public static TreeMap<BigDecimal, Double> Addition(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        if (!a.firstKey().equals(b.firstKey()) && !a.lastKey().equals(b.lastKey())) {
            return null;
        }

        TreeMap<BigDecimal, Double> result = new TreeMap<>(a);

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            if (e.getValue() != null) {
                result.put(e.getKey(), e.getValue() + (d == null ? 0.0 : d));
            }
            else {
                result.remove(e.getKey());
            }
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Subtraction(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        if (a.firstKey().doubleValue() != b.firstKey().doubleValue() && a.lastKey().doubleValue() != b.lastKey().doubleValue()) {
            return null;
        }

        return Addition(a, Multiplication(b, -1));
    }

    public static TreeMap<BigDecimal, Double> Multiplication(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        if (a.firstKey().doubleValue() != b.firstKey().doubleValue() && a.lastKey().doubleValue() != b.lastKey().doubleValue()) {
            return null;
        }

        TreeMap<BigDecimal, Double> result = new TreeMap<>(a);

        /*for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            Double d = b.get(e.getKey());
            result.put(e.getKey(), d == null ? 0.0 : e.getValue());
        }*/

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            result.put(e.getKey(), d == null ? 0.0 : d * e.getValue());
        }

        return result;
    }

    private static TreeMap<BigDecimal, Double> Multiplication(TreeMap<BigDecimal, Double> a, double multiplier) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>(a);

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            if (e.getValue() != null) {
                result.put(e.getKey(), multiplier * e.getValue());
            }
            else {
                result.remove(e.getKey());
            }
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Division(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        if (a.firstKey().doubleValue() != b.firstKey().doubleValue() && a.lastKey().doubleValue() != b.lastKey().doubleValue()) {
            return null;
        }

        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            Double d = b.get(e.getKey());
            result.put(e.getKey(), d == null ? 0.0 : e.getValue());
        }

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            if (e.getValue() != null && e.getValue() != 0.0) {
                result.put(e.getKey(), d == null ? 0.0 : d / e.getValue());
            }
            else {
                result.remove(e.getKey());
            }

            //result.put(e.getKey(), d == null || e.getValue() == 0 ? 0.0 : d / e.getValue());
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Division(TreeMap<BigDecimal, Double> a, double divisor) {
        return Multiplication(a, 1/divisor);
    }
}
