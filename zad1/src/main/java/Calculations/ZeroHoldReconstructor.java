package Calculations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;

public class ZeroHoldReconstructor{

    private int n;

    public ZeroHoldReconstructor(int n){
        this.n = n;
    }

    public double reconstruct(BigDecimal t, TreeMap<BigDecimal, Double> signal) {
        double result = 0.0;
        ArrayList<BigDecimal> keys = new ArrayList<>(signal.keySet());
        int indexOfTOrFirstBefore = lowerBound(keys, t);
        double T = keys.get(1).doubleValue() - keys.get(0).doubleValue();
        int startIndex = indexOfTOrFirstBefore - n >= 0 ? indexOfTOrFirstBefore - n : 0;
        int endIndex = indexOfTOrFirstBefore + n - 1 < keys.size() ? indexOfTOrFirstBefore + n - 1 : keys.size() - 1;
        for (int i = startIndex; i <= endIndex; startIndex++){
            result += signal.get(keys.get(i)) * rect(t.doubleValue(), T, i - endIndex);
        }
        return result;
    }

    private int lowerBound(ArrayList<BigDecimal> array, BigDecimal value) {
        int low = 0;
        int high = array.size();
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value.compareTo(array.get(mid)) < 1) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private double rect(double t, double T, int n){
        double val = Math.abs((t - T/2 - n*T)/T);
        if(val > 0.5)
            return 0.0;
        else if( val == 0.5)
            return 0.5;
        else
            return 1.0;
    }
}
