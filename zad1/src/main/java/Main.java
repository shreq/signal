import Charts.DrawerXYLineChart;
import Signals.*;
import org.jfree.chart.ui.UIUtils;

import java.math.BigDecimal;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 50, 5);
        Signal sssoh = new SignalSinusoidalStraightenedOneHalf(1, 0, 50, 5);
        Signal sssth = new SignalSinusoidalStraightenedTwoHalf(1, 0, 50, 5);
        Signal sr = new SignalRectangular(1.2, 0, 50, 12.3, 0.7);
        Signal srs = new SignalRectangularSymmetric(1.2, 0, 50, 12.3, 0.7);
        Signal st = new SignalTriangular(1, 0, 50, 10, 0.8);
        Signal su = new StepUnit(1, 0, 50, 30);
        Signal ni = new NoiseImpulse(1, 0, 50, 1, 0.9);

        Signal signal = ss;
        TreeMap<BigDecimal, Double> map = signal.generate();

        /**/
        Signal s = new SignalSinusoidal(2, 25, 50, 5);
        TreeMap<BigDecimal, Double> map2 = Operator.Addition(map, s.generate());
        /**/

        DrawerXYLineChart d = new DrawerXYLineChart("Chart", signal.getClass().getSimpleName(), map2);
        d.pack();
        UIUtils.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}
