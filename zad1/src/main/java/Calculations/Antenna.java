package Calculations;

import java.math.BigDecimal;
import java.util.*;

public class Antenna {
    int samples;
    double timeUnit;
    double realSpeed;
    double abstractSpeed;
    double signalPeriod;
    int basicSignals;
    double fs;
    double bufferSize;
    double reportingPeriod;

    Random random = new Random();

    public List<Double> GetOriginalDistance()
    {
        List<Double> result = new ArrayList<>();

        for (double i = 0.0; i < (double)(samples) * reportingPeriod; i += reportingPeriod)
            result.add(i * realSpeed);

        return result;
    }

    public List<Double> CountDiffrence(List<Double> originalDistance, List<Double> countedDistance)
    {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < originalDistance.size(); i++)
            result.add((double) Math.round(Math.abs(originalDistance.get(i) - countedDistance.get(i))));
        return result;
    }

    public List<Double> CountDistances()
    {
        List<Double> result = new ArrayList<>();
        List<Double> amplitudes = new ArrayList<>();

        for (int i = 0; i < basicSignals; i++)
        {
            amplitudes.add(random.nextDouble() * 5.0 + 1.0);
        }

        double duration = bufferSize / fs;

        for (double i = 0.0; i < (double)(samples) * reportingPeriod; i += reportingPeriod)
        {
            double currentDistance = i * realSpeed;
            double propagationTime = 2 * currentDistance / abstractSpeed;

            TreeMap<BigDecimal, Double> probingSignal = CreateSignal(amplitudes, signalPeriod, i - duration, duration, fs);
            TreeMap<BigDecimal, Double> feedbackSignal = CreateSignal(amplitudes, signalPeriod, i - propagationTime, duration, fs);

            TreeMap<BigDecimal, Double> correlationSamples = Correlation.correlate(probingSignal, feedbackSignal);

            result.add(CalculateDistance(correlationSamples, fs, abstractSpeed));
        }

        return result;
    }

    private static double CalculateDistance(TreeMap<BigDecimal, Double> correlation, double frequency, double abstractSpeed)
    {
        List<Double> correlationValues = new ArrayList<>(correlation.values());
        List<Double> rightHalf = correlationValues.subList(correlation.size()/2, correlation.size());
        int maxSample = rightHalf.indexOf(Collections.max(rightHalf));
        double tDelay = maxSample / frequency;

        return Math.round(((tDelay * abstractSpeed) / 2));
    }


    private TreeMap<BigDecimal, Double> CreateSignal(List<Double> amplitudes, double period, double startTime, double duration, double frequency)
    {
        TreeMap<BigDecimal, Double> samples = new TreeMap<>();

        for (int i = 0; i < 1; i++ )
        {
            TreeMap<BigDecimal, Double> newSamples = new TreeMap<>();

            for (double j = startTime; j < (startTime + duration); j += 1 / frequency)
            {
                newSamples.put(new BigDecimal(j) ,AntenaSinusoidalSignal(amplitudes.get(i), period, j));
            }

            samples = newSamples;
        }

        return samples;
    }


    private double AntenaSinusoidalSignal(double A, double T, double t)
    {
        return A * Math.sin((2 * Math.PI / T) * t);
    }
}
