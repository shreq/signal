package Calculations;

import org.apache.commons.math3.complex.Complex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Fourier {

    public static long timeDFT;
    public static long timeFFT;

    public static ArrayList<Complex> discreteFourierTransform(TreeMap<BigDecimal, Double> signal) {
        Double[] values = signal.values().toArray(new Double[0]);

        ArrayList<Complex> points = toComplex(values);
        ArrayList<Complex> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < points.size(); i++) {
            Complex x = new Complex(0);

            for (int j = 0; j < points.size(); j++) {
                x = new Complex(points.get(j).getReal(), points.get(j).getImaginary()).multiply(coreFactor(i, j, points.size())).add(x);
            }

            result.add(x.divide(points.size()));
        }
        timeDFT = System.currentTimeMillis() - startTime;

        return result;
    }

    public static TreeMap<BigDecimal, Double> discreteFourierTransformBackward(List<Complex> points) {
        TreeMap<BigDecimal, Double> result = new TreeMap<>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < points.size(); i++) {
            double real = 0;

            for (int j = 0; j < points.size(); j++) {
                real += points.get(j).multiply(reverseCoreFactor(i, j, points.size())).getReal();
            }

            result.put(new BigDecimal(i), real);
        }
        timeDFT = System.currentTimeMillis() - startTime;

        return result;
    }

    public static ArrayList<Complex> fastFourierTransform(TreeMap<BigDecimal, Double> signal) {
        Double[] values = signal.values().toArray(new Double[0]);

        ArrayList<Complex> points = toComplex(values);
        ArrayList<Complex> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < points.size(); i++) {

        }
        timeFFT = System.currentTimeMillis() - startTime;

        return result;
    }

    public static TreeMap<BigDecimal, Double> fastFourierTransformBackward(List<Complex> points) {
        return null;
    }

    private static ArrayList<Complex> toComplex(Double[] array) {
        ArrayList<Complex> result = new ArrayList<>();

        for (Double number : array) {
            result.add(new Complex(number, 0));
        }

        return result;
    }

    private static Complex reverseCoreFactor(int m, int n, int N) {
        return new Complex(0, 2.0 * Math.PI * m * n / N).exp();
    }

    private static Complex coreFactor(int m, int n, int N) {
        return new Complex(0, -2.0 * Math.PI * m * n / N).exp();
    }
}
