package CRandom;

public class Random {
    private static java.util.Random r = new java.util.Random();

    public static float random(float min, float max) {
        return r.nextFloat() * (max - min) + min;
    }

    public static float randomGaussian(float min, float max) {
        return (float) r.nextGaussian() * (max - min) + min;
    }
}
