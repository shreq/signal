package main.java.Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalRectangularSymmetric implements Signal {

    public double A;     // amplitude
    public double t1;    // time start
    public double d;     // signal duration
    public double T;     // basic period
    public double kw;    // fill factor

    public SignalRectangularSymmetric(double A, double t1, double d, double T, double kw) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
        this.kw = kw;
    }

    @Override
    public Map<Double, Double> generate(double fs) {
        Map<Double, Double> map = new TreeMap<>();

        double tx = t1 + d;
        double Ts = 1 / fs;
        for (double t = t1; t < tx; t += Ts) {
            map.put(t, ((t % T < kw * T) ? A : -A));
        }

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return generate(SAMPLES / (t1 + d));
    }
}
