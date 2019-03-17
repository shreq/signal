package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class SignalSinusoidalStraightenedOneHalf implements Signal {

    public double A;        // amplitude
    public BigDecimal t1;   // time start
    public BigDecimal d;    // signal duration
    public BigDecimal T;    // basic period

    public SignalSinusoidalStraightenedOneHalf(double A, BigDecimal t1, BigDecimal d, BigDecimal T) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
    }

    public SignalSinusoidalStraightenedOneHalf(double A, double t1, double d, double T) {
        this.A = A;
        this.t1 = new BigDecimal(t1);
        this.d = new BigDecimal(d);
        this.T = new BigDecimal(T);
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        double c = (2.0 * Math.PI) / T.doubleValue();
        for (BigDecimal t = t1; t.compareTo(tx) < 0; t = t.add(Ts)) {
            map.put(t, 0.5 * A * (Math.sin(c * (t.doubleValue() - t1.doubleValue())) + Math.abs(Math.sin(c * (t.doubleValue() - t1.doubleValue())))));
        }

        return map;
    }

    @Override
    public TreeMap<BigDecimal, Double> generate() {
        return generate(new BigDecimal(SAMPLES).divide(t1.add(d), SCALE, RoundingMode.CEILING));
    }
}
