package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class ImpulseUnit implements Signal {

    public double A;        // amplitude
    public BigDecimal ns;   // number of sample where step occurs
    public BigDecimal n1;   // time range
    //public double l;        // ?
    public double d;        // duration

    public ImpulseUnit(){}

    public ImpulseUnit(double A, BigDecimal ns, BigDecimal n1,/* double l,*/ double d) {
        this.A = A;
        this.ns = ns;
        this.n1 = n1;
        //this.l = l;
        this.d = d;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {

        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal end = new BigDecimal(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = n1.negate(); t.compareTo(end) <= 0; t = t.add(Ts)) {
            map.put(t, (t.subtract(ns).compareTo(BigDecimal.ZERO)) == 0 ? A : 0.0);
        }
        return map;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(double fs) {
        return generate(new BigDecimal(fs));
    }

    @Override
    public TreeMap<BigDecimal, Double> generate() {
        return null;
    }

    @Override
    public void setAllFields(double... params) {
        this.A = params[0];
        this.ns = new BigDecimal(params[1]);
        this.n1 = new BigDecimal(params[2]);
        //this.l = params[3];
        this.d = params[3];
    }
}
