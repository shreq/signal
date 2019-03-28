package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class ImpulseUnit implements Signal {

    public double A;        // amplitude
    public BigDecimal ns;   // number of sample where step occurs
    public BigDecimal n1;   // time range
    //public double l;        // ?
    public double f;        // frequency

    public ImpulseUnit(){}

    public ImpulseUnit(double A, BigDecimal ns, BigDecimal n1,/* double l,*/ double f) {
        this.A = A;
        this.ns = ns;
        this.n1 = n1;
        //this.l = l;
        this.f = f;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        fs = new BigDecimal(f);

        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = n1;
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = n1.negate(); t.compareTo(tx) <= 0; t = t.add(Ts)) {
            map.put(t, (t.compareTo(t.subtract(ns)) == 0 ? A : 0.0));
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
        this.f = params[3];
    }
}
