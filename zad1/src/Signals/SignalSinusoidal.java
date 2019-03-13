package Signals;

import java.util.HashMap;
import java.util.Map;

public class SignalSinusoidal extends Signal {

    public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period

    public SignalSinusoidal(SignalType signalType, float A, float t1, float d, float T) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
        this.T = T;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> map = new HashMap<>();

        return map;
    }
}
