package Calculations;

import java.math.BigDecimal;
import java.util.*;

public class SincReconstructor{

    public TreeMap<BigDecimal, Double> reconstruct(TreeMap<BigDecimal, Double> signal, double fs ,double k) {
        double newfs = fs*k;
        double duration = signal.lastKey().doubleValue();
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        ArrayList<Double> interpolated = new ArrayList<>(Collections.nCopies((int) (duration * newfs), 0.0));
        double step = duration/interpolated.size();
        ArrayList<Double> time = new ArrayList<>();
        for(int i=0; i<interpolated.size(); i++){
            time.add(i*step*fs);
        }
        for(double v : signal.values()){
            for(int i = 0; i < interpolated.size(); i++){
                interpolated.set(i, interpolated.get(i) + v * sinc(time.get(i)));
                time.set(i, time.get(i) - 1);
            }
        }
        double timeStep = (signal.lastKey().doubleValue() - signal.firstKey().doubleValue())/time.size();
        for(int i = 0; i<time.size(); i++){
            result.put(new BigDecimal(i*timeStep), interpolated.get(i));
        }
        return result;
    }

    private double sum(double t, double Ts, Double[] values, int index, int radius) {
        int left = Math.max(index - radius, 0);
        int right = Math.min(index + radius, values.length - 1);

        double sum = 0.0;
        for (int i = left; i < right; i++) {
            sum += values[i] * sinc(t / Ts - i);
        }
        return sum;
    }

    private double sinc(double t) {
        return t == 0.0 ? 1.0 : Math.sin(Math.PI * t) / (Math.PI * t);
    }
}
