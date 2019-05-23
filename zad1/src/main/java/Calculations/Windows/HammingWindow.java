package Calculations.Windows;

public class HammingWindow implements Window {

    @Override
    public double calculate(int n, int m) {
        return 0.53836 - 0.46164 * Math.cos(2.0 * Math.PI * n / m);
    }
}
