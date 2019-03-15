package main.java.Signals;

import java.util.Map;
import java.util.TreeMap;

public class NoiseImpulse implements Signal {

    public double A;     // amplitude
    public double t1;    // time start
    public double d;     // signal duration
    public double f;     // ?
    public double p;     // ?

    public NoiseImpulse(double A, double t1, double d, double f, double p) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.f = f;
        this.p = p;
    }

    @Override
    public Map<Double, Double> generate(double fs) {
        Map<Double, Double> map = new TreeMap<>();

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return null;
    }
}
