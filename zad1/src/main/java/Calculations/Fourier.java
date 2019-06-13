package Calculations;

import org.apache.commons.math3.complex.Complex;

import java.awt.peer.ComponentPeer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

        long startTime = System.currentTimeMillis();
        ArrayList<Complex> result = switchSamples(points, false);
        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i).divide(points.size()));
        }
        timeFFT = System.currentTimeMillis() - startTime;

        return result;
    }

    /*public static ArrayList<Complex> fastFourierTransform(ArrayList<Complex> points) {
        if (points.size() < 2) {
            return points;
        }

        ArrayList<Complex> evens = new ArrayList<>();
        ArrayList<Complex> odds = new ArrayList<>();

        for (int i = 0; i < points.size() / 2; i++) {
            Complex x = points.get(i);
            Complex y = points.get(i + points.size() / 2);

            evens.add(points.get(i).add(points.get(i + points.size() / 2)));
            odds.add(points.get(i).subtract(points.get(i + points.size() / 2)).multiply(coreFactor(i, 1, points.size())));
        }

        ArrayList<Complex> xevensx = fastFourierTransform(evens);
        ArrayList<Complex> xoddsx = fastFourierTransform(odds);

        List<Complex> result = Arrays.asList(new Complex[points.size()]);
        for (int i = 0; i < points.size() / 2; i++) {
            result.set(2 * i, xevensx.get(i));
            result.set(2 * i + 1, xoddsx.get(i));
        }

        return new ArrayList<>(result);
    }

    public static ArrayList<Complex> fastFourierTransform(TreeMap<BigDecimal, Double> signal) {
        return fastFourierTransform(toComplex(signal.values().toArray(new Double[0])));
    }*/

    public static TreeMap<BigDecimal, Double> fastFourierTransformBackward(List<Complex> points) {
        long startTime = System.currentTimeMillis();
        ArrayList<Complex> transformed = switchSamples(points, true);

        TreeMap<BigDecimal, Double> result = new TreeMap<>();
        for (int i = 0; i < transformed.size(); i++) {
            result.put(new BigDecimal(i), transformed.get(i).getReal());
        }
        timeFFT = System.currentTimeMillis() - startTime;

        return result;
    }

    private static ArrayList<Complex> switchSamples(List<Complex> points, boolean reverseOrder) {
        if (points.size() < 2) {
            return (ArrayList<Complex>) points;
        }

        ArrayList<Complex> odds = new ArrayList<>();
        ArrayList<Complex> evens = new ArrayList<>();

        for (int i = 0; i < points.size() / 2; i++) {
            odds.add(points.get(2 * i + 1));
            evens.add(points.get(2 * i));
        }

        return connect(switchSamples(evens, reverseOrder), switchSamples(odds, reverseOrder), reverseOrder);
    }

    private static ArrayList<Complex> connect(List<Complex> evens, List<Complex> odds, boolean reverseOrder) {
        ArrayList<Complex> resultLeft = new ArrayList<>();
        ArrayList<Complex> resultRight = new ArrayList<>();

        for (int i = 0; i < odds.size(); i++) {
            if (reverseOrder) {
                resultLeft.add(evens.get(i).add(reverseCoreFactor(i, 1, 2 * odds.size()).multiply(odds.get(i))));
                resultRight.add(evens.get(i).subtract(reverseCoreFactor(i, 1, 2 * odds.size()).multiply(odds.get(i))));
            }
            else {
                resultLeft.add(evens.get(i).add(coreFactor(i, 1, 2 * odds.size()).multiply(odds.get(i))));
                resultRight.add(evens.get(i).subtract(coreFactor(i, 1, 2 * odds.size()).multiply(odds.get(i))));
            }
        }
        resultLeft.addAll(resultRight);

        return resultLeft;
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
