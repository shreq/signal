package Signals;

import java.util.HashMap;
import java.util.Map;

public class NoiseImpulse extends Signal {

    public float t1;    // time start
    public float d;     // signal duration
    public float f;     // ?
    public float p;     // ?

    public NoiseImpulse(SignalType signalType, float A, float t1, float d, float f, float p) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
        this.f = f;
        this.p = p;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> map = new HashMap<>();

        return map;
    }
}
