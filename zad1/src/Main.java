import Charts.Drawer;
import Signals.NoiseUniformDistribution;
import Signals.Signal;
import Signals.SignalType;
import org.jfree.ui.RefineryUtilities;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Signal s = new NoiseUniformDistribution(SignalType.NoiseUniformDistribution, 3.3f, 0, 5);
        HashMap<Double, Double> map = (HashMap<Double, Double>) s.generate(50);

        Drawer d = new Drawer("Chart", "NoiseUniformDistribution", map);
        d.pack();
        RefineryUtilities.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}
