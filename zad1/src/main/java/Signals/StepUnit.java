package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class StepUnit implements Signal {

    public double A;        // amplitude
    public BigDecimal t1;   // time start
    public BigDecimal d;    // signal duration
    public BigDecimal ts;   // time step

    public StepUnit(double A, BigDecimal t1, BigDecimal d, BigDecimal ts) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.ts = ts;
    }

    public StepUnit(double A, double t1, double d, double ts) {
        this.A = A;
        this.t1 = new BigDecimal(t1);
        this.d = new BigDecimal(d);
        this.ts = new BigDecimal(ts);
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = t1; t.compareTo(tx) <= 0; t = t.add(Ts)) {
            if (t.compareTo(ts) > 0) {
                map.put(t, A);
            }
            else if (t.compareTo(ts) < 0) {
                map.put(t, 0.0);
            }
            else {
                map.put(t, 0.5);
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
        return generate(new BigDecimal(SAMPLES).divide(d, SCALE, RoundingMode.CEILING));
    }
}
