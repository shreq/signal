import Signals.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Calculator {

    //region Trimmers
    public static TreeMap<BigDecimal, Double> Trim(BigDecimal d, BigDecimal T, TreeMap<BigDecimal, Double> map) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        int i = (int) Math.floor(d.doubleValue() / T.doubleValue());
        BigDecimal t = T.multiply(new BigDecimal(i));
        for (Map.Entry<BigDecimal, Double> e : map.entrySet()) {

            if (e.getKey().compareTo(t) <= 0) {
                result.put(e.getKey(), e.getValue());
            }
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> Trim(SignalRectangular signal, TreeMap<BigDecimal, Double> map) {
        return Trim(signal.d, signal.T, map);
    }

    public static TreeMap<BigDecimal, Double> Trim(SignalRectangularSymmetric signal, TreeMap<BigDecimal, Double> map) {
        return Trim(signal.d, signal.T, map);
    }

    public static TreeMap<BigDecimal, Double> Trim(SignalSinusoidal signal, TreeMap<BigDecimal, Double> map) {
        return Trim(signal.d, signal.T, map);
    }

    public static TreeMap<BigDecimal, Double> Trim(SignalSinusoidalStraightenedOneHalf signal, TreeMap<BigDecimal, Double> map) {
        return Trim(signal.d, signal.T, map);
    }

    public static TreeMap<BigDecimal, Double> Trim(SignalSinusoidalStraightenedTwoHalf signal, TreeMap<BigDecimal, Double> map) {
        return Trim(signal.d, signal.T, map);
    }

    public static TreeMap<BigDecimal, Double> Trim(SignalTriangular signal, TreeMap<BigDecimal, Double> map) {
        return Trim(signal.d, signal.T, map);
    }
    //endregion

    public static double Mean(TreeMap<BigDecimal, Double> a) {
        double sum = 0.0;

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            sum += e.getValue();
        }

        return sum / a.size();
    }

    public static double MeanAbsolute(TreeMap<BigDecimal, Double> a) {
        double sum = 0.0;
        double mean = Mean(a);

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            sum += Math.abs(e.getValue() - mean);
        }

        return sum / a.size();
    }

    public static double RootMeanSquare(TreeMap<BigDecimal, Double> a) {
        double sum = 0.0;

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            sum += e.getValue() * e.getValue();
        }

        return Math.sqrt(sum / a.size());
    }

    public static double Variance(TreeMap<BigDecimal, Double> a) {
        double sum = 0.0;
        double mean = Mean(a);

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            sum += Math.pow(e.getValue() - mean, 2);
        }

        return sum / a.size();
    }

    public static double AvgPower(TreeMap<BigDecimal, Double> a) {
        return 0;
    }
}
