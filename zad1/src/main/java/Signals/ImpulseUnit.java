package Signals;

import java.math.BigDecimal;
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
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();



        return map;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate() {
        return null;
    }
}
