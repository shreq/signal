package Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalTriangular implements Signal {

    public double A;     // amplitude
    public double t1;    // time start
    public double d;     // signal duration
    public double T;     // basic period
    public double kw;    // fill factor

    public SignalTriangular(double A, double t1, double d, double T, double kw) {
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
        double c1 = A / (kw * T);
        double c2 = -A / (T * (1 - kw));
        for (double t = t1; t < tx; t += Ts) {
            if (t % T < kw * T) {
                map.put(t, c1 * (t % T));
            }
            else {
                map.put(t, c2 * ((t % T) - T));
            }
        }

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return generate(SAMPLES / (t1 + d));
    }
}
