package main.java.Signals;

import java.util.HashMap;

public enum SignalType {
    NoiseUniformDistribution(1),
    NoiseGaussian(2),
    SignalSinusoidal(3),
    SignalSinusoidalStraightenedOneHalf(4),
    SignalSinusoidalStraightenedTwoHalf(5),
    SignalRectangular(6),
    SignalRectangularSymmetric(7),
    SignalTriangular(8),
    StepUnit(9),
    ImpulseUnit(10),
    NoiseImpulse(11);

    private int value;
    private static HashMap map = new HashMap<>();

    SignalType(int value) {
        this.value = value;
    }

    static {
        for (SignalType signalType : SignalType.values())
            map.put(signalType.value, signalType);
    }

    public static SignalType valueOf(int signalType) {
        return (SignalType) map.get(signalType);
    }

    public int getValue() {
        return value;
    }
}
