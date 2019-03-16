package main.java.Signals;

import main.java.CRandom.Random;

import java.util.Map;
import java.util.TreeMap;

public class NoiseImpulse implements Signal {

    public double A;     // amplitude
    public double t1;    // time start
    public double d;     // signal duration
    public double f;     // frequency       // ? ? ?
    public double p;     // probability

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

        double tx = t1 + d;
        double Ts = 1 / fs;
        for (double t = t1; t < tx; t += Ts) {
            map.put(t, (Random.random(0, 1) < p) ? A : 0.0);
        }

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return generate(SAMPLES / (t1 + d));
    }
}
