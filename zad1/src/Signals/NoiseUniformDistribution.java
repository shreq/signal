package Signals;

import CRandom.Random;

import java.util.Map;
import java.util.TreeMap;

public class NoiseUniformDistribution implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration

    public NoiseUniformDistribution(float A, float t1, float d) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> map = new TreeMap<>();

        float tx = t1 + d;
        double Ts = 1 / fs;
        for (double t = t1; t < tx; t += Ts) {
            map.put(t, Random.random(-A, A));
        }

        return map;
    }
}
