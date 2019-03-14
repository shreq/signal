package Signals;

import java.util.Map;

public interface Signal {

    Map<Double, Double> generate(float fs);
}
