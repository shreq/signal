package Signals;

import java.util.Map;

public interface Signal {
    short SAMPLES = 500;

    Map<Double, Double> generate(double fs);
    Map<Double, Double> generate();
}
