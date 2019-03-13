package Signals;

import java.util.HashMap;
import java.util.Map;

public class SignalSinusoidalStraightenedOneHalf extends Signal {

    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period

    public SignalSinusoidalStraightenedOneHalf(SignalType signalType, float A, float t1, float d, float T) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
        this.T = T;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> chart = new HashMap<>();

        return chart;
    }
}
