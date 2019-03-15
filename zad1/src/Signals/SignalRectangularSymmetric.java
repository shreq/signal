package Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalRectangularSymmetric implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period
    public float kw;    // fill factor

    public SignalRectangularSymmetric(float A, float t1, float d, float T, float kw) {
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
        for (double t = t1; t < tx; t += Ts) {
            if (t % T < kw * T) {
                map.put(t, (double) A);
            } else {
                map.put(t, (double) -A);
            }
        }

        return map;
    }
}
