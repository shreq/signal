package Signals;

import CRandom.Random;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class NoiseUniformDistribution implements Signal {

    public double A;        // amplitude
    public BigDecimal t1;   // time start
    public BigDecimal d;    // signal duration

    public NoiseUniformDistribution(double A, BigDecimal t1, BigDecimal d) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
    }

    public NoiseUniformDistribution(double A, double t1, double d) {
        this.A = A;
        this.t1 = new BigDecimal(t1);
        this.d = new BigDecimal(d);
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = t1; t.compareTo(tx) < 0; t = t.add(Ts)) {
            map.put(t, Random.random(-A, A));
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
