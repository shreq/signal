package Signals;

import CRandom.Random;

import java.util.HashMap;
import java.util.Map;

public class NoiseGaussian extends Signal {

    public float t1;    // time start
    public float d;     // signal duration

    public NoiseGaussian(SignalType signalType, float A, float t1, float d) {
        super(signalType, A);
        this.t1 = t1;
        this.d = d;
    }

    @Override
    public Map<Float, Float> generate(float fs) {
        Map<Float, Float> chart = new HashMap<>();

        float tx= t1+d;
        float Ts = 1/fs;
        for(float t=t1; t<tx; t+=Ts) {

        }

        return chart;
    }
}
