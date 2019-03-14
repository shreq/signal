package Signals;

import java.util.Map;
import java.util.TreeMap;

public class NoiseImpulse implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration
    public float f;     // ?
    public float p;     // ?

    public NoiseImpulse(float A, float t1, float d, float f, float p) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.f = f;
        this.p = p;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> chart = new TreeMap<>();

        return chart;
    }
}
