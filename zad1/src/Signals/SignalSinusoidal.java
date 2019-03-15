package Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalSinusoidal implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period

    public SignalSinusoidal(float A, float t1, float d, float T) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> map = new TreeMap<>();

        float tx = t1 + d;
        fs = 500 / tx;

        double Ts = 1 / fs;
        double c = (2.0 * Math.PI) / T;
        for (double t = t1; t < tx; t += Ts) {
            map.put(t, A * Math.sin(c * (t - t1)));
        }

        return map;
    }
}
