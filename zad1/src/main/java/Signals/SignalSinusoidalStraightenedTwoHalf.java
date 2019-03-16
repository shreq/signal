package Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalSinusoidalStraightenedTwoHalf implements Signal {

    public double A;     // amplitude
    public double t1;    // time start
    public double d;     // signal duration
    public double T;     // basic period

    public SignalSinusoidalStraightenedTwoHalf(double A, double t1, double d, double T) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
    }

    @Override
    public Map<Double, Double> generate(double fs) {
        Map<Double, Double> map = new TreeMap<>();

        double tx = t1 + d;
        double Ts = 1 / fs;
        double c = (2.0 * Math.PI) / T;
        for (double t = t1; t < tx; t += Ts) {
            map.put(t, A * Math.abs(Math.sin(c * (t - t1))));
        }

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return generate(SAMPLES / (t1 + d));
    }
}
