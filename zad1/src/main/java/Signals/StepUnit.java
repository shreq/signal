package main.java.Signals;

import java.util.Map;
import java.util.TreeMap;

public class StepUnit implements Signal {

    public double A;     // amplitude
    public double t1;    // time start
    public double d;     // signal duration
    public double ts;    // time step

    public StepUnit(double A, double t1, double d, double ts) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.ts = ts;
    }

    @Override
    public Map<Double, Double> generate(double fs) {
        Map<Double, Double> map = new TreeMap<>();

        double tx = t1 + d;
        double Ts = 1 / fs;
        for (double t = t1; t < tx; t += Ts) {
            if (t > ts) {
                map.put(t, A);
            }
            else if (t < ts) {
                map.put(t, 0.0);
            }
            else {
                map.put(t, 0.5);
            }
        }

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return generate(SAMPLES / (t1 + d));
    }
}
