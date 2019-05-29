package Calculations;

import java.math.BigDecimal;
import java.util.*;

public class Antenna {

    private TreeMap<BigDecimal, Double> signal;
    private double velocity;
    private long T;

    private double objVelocity;
    private boolean active;
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

    public void simulate() throws InterruptedException {
        while (active) {
            TreeMap<BigDecimal, Double> shifted = shift(signal, -calculateShift());
            TreeMap<BigDecimal, Double> correlation = Correlation.correlate(signal, shifted);

            double time = getKeyForMaximum(correlation);
            distance = velocity * time / 2.0;

            Thread.sleep(T);
            timer += T;
        }
    }

    private double calculateShift() {
        return (startingDistance / velocity) + (objVelocity * timer / velocity);
    }

    private static TreeMap<BigDecimal, Double> shift(TreeMap<BigDecimal, Double> signal, double time) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        ArrayList<BigDecimal> keys = new ArrayList<>(signal.keySet());
        BigDecimal T = keys.get(1).subtract(keys.get(0));

        BigDecimal t0 = signal.firstKey();
        BigDecimal tx = roundToClosestKey(signal.keySet(), time, 3);
        for (int i = 0; i < signal.size(); i++) {
            result.put(t0, signal.get(tx));
            t0 = t0.add(T);
            tx = tx.compareTo(signal.lastKey()) >= 0 ? signal.firstKey() : tx.add(T);
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
            }
        }

        return maxK.doubleValue();
    }
}
