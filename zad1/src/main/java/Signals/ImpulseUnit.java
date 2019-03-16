package main.java.Signals;

import java.util.Map;
import java.util.TreeMap;

public class ImpulseUnit implements Signal {

    public double A;     // amplitude
    public double ns;    // number of sample where step occurs
    public double n1;    // number of first sample
    public double l;     // ?
    public double f;     // frequency

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
