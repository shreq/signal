import Charts.Drawer;
import Signals.NoiseGaussian;
import Signals.NoiseUniformDistribution;
import Signals.Signal;
import Signals.SignalSinusoidal;
import org.jfree.ui.RefineryUtilities;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 50, 3);

        Map<Double, Double> map = ss.generate(60);

        Drawer d = new Drawer("Chart", "Nananana", map);
        d.pack();
        RefineryUtilities.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}
