package Signals;

import java.util.Map;
import java.util.TreeMap;

public class StepUnit implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration
    public float ts;    // ?

    public StepUnit(float A, float t1, float d, float ts) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.ts = ts;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> map = new TreeMap<>();

        float tx = t1 + d;
        double Ts = 1 / fs;
        for (double t = t1; t < tx; t += Ts) {
            if (t > ts) {
                map.put(t, (double) A);
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
}
