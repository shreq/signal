package Calculations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Operator {

    public static TreeMap<BigDecimal, Double> Addition(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>(a);

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            result.put(e.getKey(), e.getValue() + (d == null ? 0.0 : d));
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Subtraction(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        return Addition(a, Multiplication(b, -1));
    }

    public static TreeMap<BigDecimal, Double> Multiplication(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            Double d = b.get(e.getKey());
            result.put(e.getKey(), d == null ? 0.0 : e.getValue());
        }

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            result.put(e.getKey(), d == null ? 0.0 : d * e.getValue());
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Multiplication(TreeMap<BigDecimal, Double> a, double multiplier) {
        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            a.put(e.getKey(), multiplier * e.getValue());
        }

        return a;
    }

    public static TreeMap<BigDecimal, Double> Division(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            Double d = b.get(e.getKey());
            result.put(e.getKey(), d == null ? 0.0 : e.getValue());
        }

        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            Double d = result.get(e.getKey());
            if (e.getValue() != 0) {
                result.put(e.getKey(), d == null ? 0.0 : d / e.getValue());
            }
            else {
                result.put(e.getKey(), (double) Float.MAX_VALUE);
            }
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Division(TreeMap<BigDecimal, Double> a, double divisor) {
        return Multiplication(a, 1/divisor);
    }
}
