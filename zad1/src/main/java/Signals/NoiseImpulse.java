package Signals;

import CRandom.Random;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class NoiseImpulse implements Signal {

    public double A;        // amplitude
    public BigDecimal t1;   // time start
    public BigDecimal d;    // signal duration
    public double f;        // frequency       // ? ? ?
    public double p;        // probability

    public NoiseImpulse(){}

    public NoiseImpulse(double A, BigDecimal t1, BigDecimal d, double f, double p) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.f = f;
        this.p = p;
    }

    public NoiseImpulse(double A, double t1, double d, double f, double p) {
        this.A = A;
        this.t1 = new BigDecimal(t1);
        this.d = new BigDecimal(d);
        this.f = f;
        this.p = p;
    }

    @Override
    public void setAllFields(double... params){
        this.A = params[0];
        this.t1 = new BigDecimal(params[1]);
        this.d = new BigDecimal(params[2]);
        this.f = params[3];
        this.p = params[4];
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = t1; t.compareTo(tx) <= 0; t = t.add(Ts)) {
            map.put(t, (Random.random(0, 1) < p) ? A : 0.0);
        }

        return map;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(double fs) {
        return generate(new BigDecimal(fs));
    }

    @Override
    public TreeMap<BigDecimal, Double> generate() {
        return generate(new BigDecimal(SAMPLES).divide(d, SCALE, RoundingMode.CEILING));
    }
}
