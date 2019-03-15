package Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalTriangular implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period
    public float kw;     // fill factor

    public SignalTriangular(float A, float t1, float d, float T, float kw) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
        this.kw = kw;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> map = new TreeMap<>();

        float tx = t1 + d;
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
}
