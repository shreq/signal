package main.java.Signals;

import java.util.Map;
import java.util.TreeMap;

public class ImpulseUnit implements Signal {

    public double A;     // amplitude
    public double ns;    // ?
    public double n1;    // ?
    public double l;     // ?
    public double f;     // ?

    public ImpulseUnit(double A, double ns, double n1, double l, double f) {
        this.A = A;
        this.ns = ns;
        this.n1 = n1;
        this.l = l;
        this.f = f;
    }

    @Override
    public Map<Double, Double> generate(double fs) {
        Map<Double, Double> map = new TreeMap<>();

        return map;
    }

    @Override
    public Map<Double, Double> generate() {
        return null;
    }
}
