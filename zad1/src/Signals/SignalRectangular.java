package Signals;

import java.util.HashMap;
import java.util.Map;

public class SignalRectangular extends Signal {

    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period
    public float k;     // fill factor

    public SignalRectangular(SignalType signalType, float A, float t1, float d, float T, float k) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
        this.T = T;
        this.k = k;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> chart = new HashMap<>();

        return chart;
    }
}