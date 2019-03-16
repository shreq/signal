import Charts.Drawer;
import Signals.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ui.UIUtils;

import java.util.Map;

public class Main extends Application {

    public static void main(String[] args) {

        //launch(args);

        Signal nud = new NoiseUniformDistribution(1, 0, 50);
        Signal ng = new NoiseGaussian(0, 50);
        Signal ss = new SignalSinusoidal(1, 0, 0.005, 0.002);
        Signal sssoh = new SignalSinusoidalStraightenedOneHalf(1, 0, 50, 5);
        Signal sssth = new SignalSinusoidalStraightenedTwoHalf(1, 0, 50, 5);
        Signal sr = new SignalRectangular(1.2, 0, 50, 12.3, 0.7);
        Signal srs = new SignalRectangularSymmetric(1.2, 0, 50, 12.3, 0.7);
        Signal st = new SignalTriangular(1, 0, 50, 10, 0.8);
        Signal su = new StepUnit(1, 0, 50, 30);
        Signal ni = new NoiseImpulse(1, 0, 50, 1, 0.9);

        Signal signal = ni;
        Map<Double, Double> map = signal.generate();

        Drawer d = new Drawer("Chart", signal.getClass().getSimpleName(), map);
        d.pack();
        UIUtils.centerFrameOnScreen(d);
        d.setVisible(true);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
