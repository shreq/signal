package main.java;

import main.java.Charts.Drawer;
import main.java.Signals.*;
import org.jfree.chart.ui.UIUtils;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 0.005, 0.002);
        Signal sssoh = new SignalSinusoidalStraightenedOneHalf(1, 0, 50, 5);
        Signal sssth = new SignalSinusoidalStraightenedTwoHalf(1, 0, 50, 5);
        Signal sr = new SignalRectangular(1.2, 0, 50, 12.3, 0.7);
        Signal srs = new SignalRectangularSymmetric(1.2, 0, 50, 12.3, 0.7);
        Signal st = new SignalTriangular(1, 0, 50, 10, 0.8);
        Signal su = new StepUnit(1, 0, 50, 30);

        Map<Double, Double> map = sssoh.generate();

        Drawer d = new Drawer("Chart", "Nananana", map);
        d.pack();
        UIUtils.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}
