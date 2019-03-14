package CRandom;

public class Random {
    private static java.util.Random r = new java.util.Random();

    public static double random(double min, double max) {
        return r.nextFloat() * (max - min) + min;
    }

    public static double randomGaussian(double min, double max) {
        return r.nextGaussian() * (max - min) + min;
    }
}
