package Signals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;

public class SignalRectangularSymmetric implements Signal {

    public double A;        // amplitude
    public BigDecimal t1;   // time start
    public BigDecimal d;    // signal duration
    public BigDecimal T;    // basic period
    public double kw;       // fill factor

    public SignalRectangularSymmetric(){}

    public SignalRectangularSymmetric(double A, BigDecimal t1, BigDecimal d, BigDecimal T, double kw) {
        this.A = A;
        this.t1 = t1;
        this.d = d;
        this.T = T;
        this.kw = kw;
    }

    public SignalRectangularSymmetric(double A, double t1, double d, double T, double kw) {
        this.A = A;
        this.t1 = new BigDecimal(t1);
        this.d = new BigDecimal(d);
        this.T = new BigDecimal(T);
        this.kw = kw;
    }

    @Override
    public void setAllFields(double... params){
        this.A = params[0];
        this.t1 = new BigDecimal(params[1]);
        this.d = new BigDecimal(params[2]);
        this.T = new BigDecimal(params[3]);
        this.kw = params[4];
    }

    @Override
    public TreeMap<BigDecimal, Double> generate(BigDecimal fs) {
        TreeMap<BigDecimal, Double> map = new TreeMap<>();

        BigDecimal tx = t1.add(d);
        BigDecimal Ts = new BigDecimal(1).divide(fs, SCALE, RoundingMode.CEILING);
        for (BigDecimal t = t1; t.compareTo(tx) <= 0; t = t.add(Ts)) {
            map.put(t, ((t.remainder(T).compareTo(T.multiply(new BigDecimal(kw))) < 0) ? A : -A));
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
