package Signals;

import java.util.Map;

public abstract class Signal {

    public SignalType signalType;
    public float A;     // amplitude
    /*public float t1;    // time start
    public float d;     // signal duration
    public float T;     // basic period
    public float k;     // fill factor*/

    public Signal(SignalType signalType, float A) {
        this.signalType = signalType;
        this.A = A;
    }

    public abstract Map<Double, Double> generate(float fs);
}
