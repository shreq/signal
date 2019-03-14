import Charts.Drawer;
import Signals.*;
import org.jfree.ui.RefineryUtilities;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 50, 5);
        Signal sssoh = new SignalSinusoidalStraightenedOneHalf(1, 0, 50, 5);
        Signal sssth = new SignalSinusoidalStraightenedTwoHalf(1, 0, 50, 5);
        Signal sr = new SignalRectangular(1, 0, 50, 1, 0.5f); // better check this class
        Signal srs = new SignalRectangularSymmetric(1, 0, 50, 1, 0.5f); // same

        Map<Double, Double> map = sr.generate(60);

        Drawer d = new Drawer("Chart", "Nananana", map);
        d.pack();
        RefineryUtilities.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}
