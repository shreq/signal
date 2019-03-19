package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class SignalTriangular implements Signal {

    public double A;        // amplitude
    public BigDecimal t1;   // time start
    public BigDecimal d;    // signal duration
    public BigDecimal T;    // basic period
    public double kw;       // fill factor

    public SignalTriangular(double A, BigDecimal t1, BigDecimal d, BigDecimal T, double kw) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
        this.kw = kw;
    }

    public SignalTriangular(double A, double t1, double d, double T, double kw) {
        this.A = A;
        this.t1 = new BigDecimal(t1);
        this.d = new BigDecimal(d);
        this.T = new BigDecimal(T);
        this.kw = kw;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        double c1 = A / (kw * T.doubleValue());
        double c2 = -A / (T.doubleValue() * (1 - kw));
        for (BigDecimal t = t1; t.compareTo(tx) <= 0; t = t.add(Ts)) {
            if (t.remainder(T).compareTo(T.multiply(new BigDecimal(kw))) < 0) {
                map.put(t, c1 * (t.remainder(T)).doubleValue());
            }
            else {
                map.put(t, c2 * (t.remainder(T).doubleValue() - T.doubleValue()));
            }
        }

        return map;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(double fs) {
        return generate(new BigDecimal(fs));
    }

    @Override
    public TreeMap<BigDecimal, Double> generate() {
        return generate(T.multiply(new BigDecimal(5)));
    }
}
