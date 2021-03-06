package plot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlotWindow extends JFrame {

    public PlotWindow(ArrayList<Double> X, ArrayList<Double> Y) {
        Plot plot = new Plot(Y);

        this.add(plot);
        this.setPreferredSize(new Dimension(520, 520));

        this.setTitle("Transaction Chart");
        this.setVisible(true);
        this.pack();
    }
}
