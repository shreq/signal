package Signals;

import java.util.HashMap;
import java.util.Map;

public class ImpulseUnit extends Signal {

    public float ns;    // ?
    public float n1;    // ?
    public float l;     // ?
    public float f;     // ?

    public ImpulseUnit(SignalType signalType, float A, float ns, float n1, float l, float f) {
        super(signalType, A);
        this.ns = ns;
        this.n1 = n1;
        this.l = l;
        this.f = f;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> chart = new HashMap<>();

        return chart;
    }
}
