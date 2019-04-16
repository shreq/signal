package Calculations;

import java.math.BigDecimal;
import java.util.*;

public class ZeroHoldReconstructor{

    public TreeMap<BigDecimal, Double> reconstruct(TreeMap<BigDecimal, Double> signal, double fs, double k) {
        TreeMap<BigDecimal, Double> result = signal;
        double newfs = fs*k;
        BigDecimal duration = signal.lastKey();
        BigDecimal step = duration.divide(duration.multiply(new BigDecimal(newfs)));
        double currentVal = signal.firstEntry().getValue();
        for(BigDecimal i = new BigDecimal(0); i.compareTo(duration)<1; i = i.add(step)){
            if(!result.getOrDefault(i, currentVal).equals(currentVal))
                currentVal = result.get(i);
            result.put(i, result.getOrDefault(i, currentVal));
        }
//        ArrayList<BigDecimal> keys = new ArrayList<>(signal.keySet());
//        double signalT = keys.get(1).subtract(keys.get(0)).doubleValue();
//        double count = T/signalT;
//        int counter = 0;
//        double currentVal = signal.firstEntry().getValue();
//        for(Map.Entry<BigDecimal, Double> e : signal.entrySet()){
//            if(counter >= count){
//                currentVal = e.getValue();
//                counter = 0;
//            }
//            result.put(e.getKey(), currentVal);
//            ++counter;
//        }
        return result;
    }

//    @Override
//    public TreeMap<BigDecimal, Double> reconstruct(TreeMap<BigDecimal, Double> signal, int radius) {
//        TreeMap<BigDecimal, Double> result = new TreeMap<>();
//        for(Map.Entry<BigDecimal, Double> e : signal.entrySet()){
//            result.put(e.getKey(), reconstructSinglePoint(e.getKey(), signal, radius));
//        }
//        return result;
//    }
//
//    public double reconstructSinglePoint(BigDecimal t, TreeMap<BigDecimal, Double> signal, int n) {
//        double result = 0.0;
//        ArrayList<BigDecimal> keys = new ArrayList<>(signal.keySet());
//        int indexOfTOrFirstBefore = lowerBound(keys, t);
//        double T = keys.get(1).doubleValue() - keys.get(0).doubleValue();
//        int startIndex = indexOfTOrFirstBefore - n >= 0 ? indexOfTOrFirstBefore - n : 0;
//        int endIndex = indexOfTOrFirstBefore + n - 1 < keys.size() ? indexOfTOrFirstBefore + n - 1 : keys.size() - 1;
//        for (int i = startIndex; i <= endIndex; i++){
//            result += signal.get(keys.get(i)) * rect(t.doubleValue(), T, i - endIndex);
//        }
//        return result;
//    }
//
//    private int lowerBound(ArrayList<BigDecimal> array, BigDecimal value) {
//        int low = 0;
//        int high = array.size();
//        while (low < high) {
//            final int mid = (low + high) / 2;
//            if (value.compareTo(array.get(mid)) < 1) {
//                high = mid;
//            } else {
//                low = mid + 1;
//            }
//        }
//        return low;
//    }
//
//    private double rect(double t, double T, int n){
//        double val = Math.abs((t - T/2 - n*T)/T);
//        if(val > 0.5)
//            return 0.0;
//        else if( val == 0.5)
//            return 0.5;
//        else
//            return 1.0;
//    }

}
