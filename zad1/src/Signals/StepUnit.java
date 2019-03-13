package Signals;

import java.util.HashMap;
import java.util.Map;

public class StepUnit extends Signal {

    public float t1;    // time start
    public float d;     // signal duration
    public float ts;    // ?

    public StepUnit(SignalType signalType, float A, float t1, float d, float ts) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
        this.ts = ts;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> map = new HashMap<>();

        return map;
    }
}
