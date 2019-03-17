import Charts.DrawerXYLineChart;
import Charts.Histogram;
import Signals.*;
import org.jfree.chart.ui.UIUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 10, 2);
        Signal sssoh = new SignalSinusoidalStraightenedOneHalf(1, 0, 50, 5);
        Signal sssth = new SignalSinusoidalStraightenedTwoHalf(1, 0, 50, 5);
        Signal sr = new SignalRectangular(1, 4, 10, 2, 0.5);
        Signal srs = new SignalRectangularSymmetric(1, 4, 10, 4, 0.5);
        Signal st = new SignalTriangular(1, 0, 50, 10, 0.8);
        Signal su = new StepUnit(1, 0, 50, 30);
        Signal ni = new NoiseImpulse(1, 0, 50, 1, 0.9);

        Signal signal = ss;
        TreeMap<BigDecimal, Double> map1 = signal.generate();

        /**/
        Signal signal2 = srs;
        TreeMap<BigDecimal, Double> map2 = signal2.generate();
        TreeMap<BigDecimal, Double> map = Operator.Multiplication(map1, map2);
        /**/

        DrawerXYLineChart d = new DrawerXYLineChart("Chart", signal.getClass().getSimpleName(), map);
        //Histogram d = new Histogram("Chart", "Histogram", map);
        d.pack();
        UIUtils.centerFrameOnScreen(d);
        d.setVisible(true);
    }
}