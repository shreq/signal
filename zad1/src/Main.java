import Charts.Drawer;
import Signals.*;
import org.jfree.ui.RefineryUtilities;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 0.005f, 0.001f);
        Signal sssoh = new SignalSinusoidalStraightenedOneHalf(1, 0, 50, 5);
        Signal sssth = new SignalSinusoidalStraightenedTwoHalf(1, 0, 50, 5);
        Signal sr = new SignalRectangular(1, 0, 50, 10, 0.7f);
        Signal srs = new SignalRectangularSymmetric(1, 0, 50, 10, 0.7f);
        Signal st = new SignalTriangular(1, 0, 50, 10, 0.7f);
        Signal su = new StepUnit(1, 0, 50, 30);
        Signal iu = new ImpulseUnit(1, 10, 2, 1, 30);

        Map<Double, Double> map = ss.generate(60);

        Drawer d = new Drawer("Chart", "Nananana", map);
        d.pack();
        RefineryUtilities.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}
