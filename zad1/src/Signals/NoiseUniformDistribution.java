package Signals;

import CRandom.Random;

import java.util.HashMap;
import java.util.Map;

public class NoiseUniformDistribution extends Signal {

    public float t1;    // time start
    public float d;     // signal duration

    public NoiseUniformDistribution(SignalType signalType, float A, float t1, float d) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> chart = new HashMap<>();

        float tx = t1+d;
        float Ts = 1/fs;
        for(float t=t1; t<tx; t+=Ts) {
            chart.put(t, Random.random(-A, A));
        }

        return chart;
    }
}
