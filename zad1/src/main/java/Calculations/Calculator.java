package Calculations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Calculator {

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
        return Math.sqrt(AvgPower(a));
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
        double sum = 0.0;

        for (Map.Entry<BigDecimal, Double> e : a.entrySet()) {
            sum += e.getValue() * e.getValue();
        }

        return sum / a.size();
    }

    public static double MeanSquareError(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        double mse = 0;
        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            mse += (a.get(e.getKey()) - e.getValue()) * (a.get(e.getKey()) - e.getValue());
        }
        return mse / b.size();
    }

    public static double SignalNoiseRatio(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b) {
        double up = 0, down = 0;
        for (Map.Entry<BigDecimal, Double> e : b.entrySet()) {
            up += a.get(e.getKey()) * a.get(e.getKey());
            down += (a.get(e.getKey()) - e.getValue()) * (a.get(e.getKey()) - e.getValue());
        }
        return 10 * Math.log10(up / down);
    }

    public static double PeakSignalNoiseRatio(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b){
        return 10 * Math.log10(Collections.max(a.values())/MeanSquareError(a, b));
    }

    public static double MaximumDifference(TreeMap<BigDecimal, Double> a, TreeMap<BigDecimal, Double> b){
        double max = 0;
        double buff = 0;
        for (Map.Entry<BigDecimal, Double> e : b.entrySet()){
            buff = Math.abs(a.get(e.getKey()) - e.getValue());
            if(buff > max)
                max = buff;
        }
        return max;
    }
}
