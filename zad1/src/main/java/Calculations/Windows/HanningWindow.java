package Calculations.Windows;

public class HanningWindow implements Window {

    @Override
    public double calculate(int n, int m) {
        return 0.5 - 0.5 * Math.cos(2.0 * Math.PI * n / m);
    }
}
