package Calculations;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

public class Antenna {
    private static int PRECISION = 2;

    private TreeMap<BigDecimal, Double> signal;
    private double velocity;
    private long T;

    private double objVelocity;
    public boolean active = false;
    private double startingDistance;

    double distance;
    double timer;

    public Antenna(TreeMap<BigDecimal, Double> signal, double velocity, long t, double objVelocity, boolean active, double startingDistance) {
        this.signal = signal;
        this.velocity = velocity;
        this.T = t;
        this.objVelocity = objVelocity;
        this.active = active;
        this.startingDistance = startingDistance;
    }

    public void simulate(JLabel label) throws InterruptedException {
        while (active) {
            double shift = calculateShift() % signal.lastKey().doubleValue();
            TreeMap<BigDecimal, Double> shifted = shift(signal, shift);
            TreeMap<BigDecimal, Double> correlation = Correlation.correlate(signal, shifted);

            double time = getKeyForMaximum(correlation) - getKeyForMiddle(correlation);
            distance = velocity * time / 2.0;
            label.setText(Double.toString(((int) (distance * 100000.0)) / 100000.0));
            Thread.sleep(T);
            timer += T;
        }
    }

    private double calculateShift() {
        return 2 * ((startingDistance / velocity) + (objVelocity * (timer / 1000.0) / velocity));
    }

    private static TreeMap<BigDecimal, Double> shift(TreeMap<BigDecimal, Double> signal, double time) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        ArrayList<BigDecimal> keys = new ArrayList<>(signal.keySet());
        BigDecimal T = keys.get(1).subtract(keys.get(0));

        BigDecimal t0 = signal.firstKey();

        if (time == 0.0) {
            return signal;
        }
        if (time > 0.0) {
            BigDecimal tx = roundToClosestKey(signal.keySet(), time, 2);
            for (int i = 0; i < signal.size(); i++) {
                result.put(t0, signal.get(tx));
                t0 = t0.add(T);
                tx = tx.compareTo(signal.lastKey()) >= 0 ? signal.firstKey() : tx.add(T);
            }
        }
        else {
            BigDecimal tx = roundToClosestKey(signal.keySet(), signal.lastKey().doubleValue() + time, 2);
            for (int i = 0; i < signal.size(); i++) {
                result.put(t0, signal.get(tx));
                t0 = t0.add(T);
                tx = tx.compareTo(signal.lastKey()) >= 0 ? signal.firstKey() : tx.add(T);
            }
        }

        return result;
    }

    private static BigDecimal roundToClosestKey(Set<BigDecimal> keys, double time0, double precision) {
        precision = Math.pow(0.1, precision);
        BigDecimal time = new BigDecimal(time0);
        for (BigDecimal key : keys) {
            if (time.compareTo(key) > 0 && time.subtract(key).doubleValue() <= precision) {
                return key;
            }
        }
        return new BigDecimal(time0);
    }

    private double getKeyForMaximum(TreeMap<BigDecimal, Double> map) {
        BigDecimal maxK = new BigDecimal(0);
        Double maxV = 0.0;
        BigDecimal[] keys = map.keySet().toArray(new BigDecimal[0]);
        Double[] values = map.values().toArray(new Double[0]);
        for (int i = map.size() / 2; i < map.size(); i++) {
            if (values[i] > maxV) {
                maxK = keys[i];
                maxV = values[i];
            }
        }

        return maxK.doubleValue();
    }

    private double getKeyForMiddle(TreeMap<BigDecimal, Double> map) {
        return map.keySet().toArray(new BigDecimal[0])[map.size() / 2].doubleValue();
    }
}
