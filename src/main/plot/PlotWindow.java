package plot;

import com.sun.tools.jconsole.JConsoleContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlotWindow extends JFrame {

    public PlotWindow(ArrayList<Double> X, ArrayList<Double> Y) {
        System.out.println(Y);
        Plot plot = new Plot(Y);
        //plot.drawChart(X, Y);

        this.add(plot);
        this.setPreferredSize(new Dimension(520, 520));



        this.setTitle("Transaction Chart");
        this.setVisible(true);
        this.pack();
    }
}
