package Signals;

import java.util.Map;
import java.util.TreeMap;

public class SignalRectangularSymmetric implements Signal {

    public float A;     // amplitude
    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period
    public float k;     // fill factor

    public SignalRectangularSymmetric(float A, float t1, float d, float T, float k) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
        this.k = k;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> chart = new TreeMap<>();

        return chart;
    }
}
