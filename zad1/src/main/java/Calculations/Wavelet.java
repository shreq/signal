package Calculations;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Wavelet {
    private static double[] H = {0.32580343, 1.01094572, 0.8922014, -0.03957503, -0.26450717, 0.0436163, 0.0465036, -0.01498699};
    private static double[] G = {-0.01498699, -0.0465036, 0.0436163, 0.26450717, -0.03957503,-0.8922014, 1.01094572, -0.32580343};

    public static ArrayList<Complex> transfrom(TreeMap<BigDecimal, Double> signal){
        TreeMap<BigDecimal, Double> treeH = new TreeMap<>();
        TreeMap<BigDecimal, Double> treeG = new TreeMap<>();
        for(int i = 0; i < H.length; i++){
            treeH.put(new BigDecimal(i), H[i]);
            treeG.put(new BigDecimal(i), G[i]);
        }
        ArrayList<Double> xH = new ArrayList<>(Convolution.convolve(signal, treeH).values());
        xH.stream().limit(signal.values().size()).collect(Collectors.toList());
        ArrayList<Double> xG = new ArrayList<>(Convolution.convolve(signal, treeG).values());
        xG.stream().limit(signal.values().size()).collect(Collectors.toList());

        ArrayList<Double> xHHalf = new ArrayList<>();
        ArrayList<Double> xGHalf = new ArrayList<>();

        ArrayList<Complex> result = new ArrayList<>();
        for(int i = 0; i<xH.size(); i++){
            if(i%2 == 0){
                xHHalf.add(xH.get(i));
            }
            else{
                xGHalf.add(xG.get(i));
            }
        }

        for(int i = 0; i < xGHalf.size(); i++){
            result.add(new Complex(xHHalf.get(i), xGHalf.get(i)));
        }

        return result;
    }

    public static TreeMap<BigDecimal, Double> backward(ArrayList<Complex> signal){
        List<Double> reversedH = Arrays.asList(ArrayUtils.toObject(H));
        List<Double> reversedG = Arrays.asList(ArrayUtils.toObject(G));
        Collections.reverse(reversedH);
        Collections.reverse(reversedG);

        ArrayList<Double> xH = new ArrayList<>();
        ArrayList<Double> xG = new ArrayList<>();

        for(int i = 0; i < signal.size(); i++){
            xH.add(signal.get(i).getReal());
            xH.add(0.0);
            xG.add(0.0);
            xG.add(signal.get(i).getImaginary());
        }
        TreeMap<BigDecimal, Double> treeH = new TreeMap<>();
        TreeMap<BigDecimal, Double> treeG = new TreeMap<>();
        for(int i = 0; i < H.length; i++){
            treeH.put(new BigDecimal(i), reversedH.get(i));
            treeG.put(new BigDecimal(i), reversedG.get(i));
        }
        TreeMap<BigDecimal, Double> treeXH = new TreeMap<>();
        TreeMap<BigDecimal, Double> treeXG = new TreeMap<>();
        for(int i = 0; i < xH.size(); i++){
            treeXH.put(new BigDecimal(i), xH.get(i));
            treeXG.put(new BigDecimal(i), xG.get(i));
        }
        treeXH = Convolution.convolve(treeXH, treeH);
        treeXG = Convolution.convolve(treeXG, treeG);
        TreeMap<BigDecimal, Double> treeXHtrimmed = new TreeMap<>();
        TreeMap<BigDecimal, Double> treeXGtrimmed = new TreeMap<>();
        int i = 0;
        for(Map.Entry<BigDecimal, Double> e : treeXH.entrySet()){
            if(i >= xH.size())
                break;
            treeXHtrimmed.put(e.getKey(), e.getValue());
            i++;
        }
        i = 0;
        for(Map.Entry<BigDecimal, Double> e : treeXG.entrySet()){
            if(i >= xG.size())
                break;
            treeXGtrimmed.put(e.getKey(), e.getValue());
            i++;
        }
        return Operator.Addition(treeXHtrimmed, treeXGtrimmed);
    }
}
