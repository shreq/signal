package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class Signal2 implements Signal {

    public BigDecimal t1;
    public BigDecimal d;

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = t1; t.compareTo(tx) <= 0; t = t.add(Ts)) {
            map.put(t, 2.0 * Math.sin(Math.PI * t.doubleValue()) + Math.sin(2.0 * Math.PI * t.doubleValue()) + 5.0 * Math.sin(4.0 * Math.PI * t.doubleValue()));
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

    @Override
    public void setAllFields(double... params) {
        this.t1 = new BigDecimal(params[0]);
        this.d = new BigDecimal(params[1]);

    }
}
