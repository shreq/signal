package Calculations.Windows;

public class BlackmanWindow implements Window {

    @Override
    public double calculate(int n, int m) {
        return 0.42 - 0.5 * Math.cos(2.0 * Math.PI * n / m) + 0.08 * Math.cos(4.0 * Math.PI * n / m);
    }
}
