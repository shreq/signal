package CRandom;

import java.util.concurrent.ThreadLocalRandom;

public class Random {

    private static java.util.Random r = new java.util.Random();

    public static double random(double min, double max) {
        if (min > max) {
            double tmp = min;
            min = max;
            max = tmp;
        }

        //return ThreadLocalRandom.current().nextDouble(min, max);  // boundaries aren't inclusive
        return r.nextDouble() * (max - min) + min;                  // are they here?
    }

    public static double randomGaussian() {
        double u1 = 1.0 - ThreadLocalRandom.current().nextDouble(0, 1); // € (0; 1]
        double u2 = 1.0 - ThreadLocalRandom.current().nextDouble(0, 1);

        return Math.sqrt(-2.0 * Math.log(u1)) * Math.sin(2.0 * Math.PI * u2); // Box–Muller transform
    }

    public static double randomGaussian(double sigma, double mu) {
        double u1 = 1.0 - ThreadLocalRandom.current().nextDouble(0, 1); // € (0; 1]
        double u2 = 1.0 - ThreadLocalRandom.current().nextDouble(0, 1);

        return mu + sigma * Math.sqrt(-2.0 * Math.log(u1)) * Math.sin(2.0 * Math.PI * u2);
    }
}
