package Signals;

import java.util.Map;
import java.util.TreeMap;

public class ImpulseUnit implements Signal {

    public float A;     // amplitude
    public float ns;    // ?
    public float n1;    // ?
    public float l;     // ?
    public float f;     // ?

    public ImpulseUnit(float A, float ns, float n1, float l, float f) {
        this.A = A;
        this.ns = ns;
        this.n1 = n1;
        this.l = l;
        this.f = f;
    }

    @Override
    public Map<Double, Double> generate(float fs) {
        Map<Double, Double> chart = new TreeMap<>();

        return chart;
    }
}
