package plot;

import com.sun.source.util.Plugin;

import javax.naming.InsufficientResourcesException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Plot extends JPanel {
    Graphics g;
    Graphics2D g2d;
    ArrayList<Double> Y;

    public Plot(ArrayList<Double> Y){
        this.Y = Y;
    }

    private void doDrawing(Graphics g) {
        this.g = g;

        this.g2d = (Graphics2D) g;
        var size = getSize();
        var insets = getInsets();
        int w = size.width - insets.left - insets.right - 20;
        int h = size.height - insets.top - insets.bottom - 20;

        g2d.setColor(Color.blue);

        g2d.setColor(Color.black);

        g2d.drawLine(20, h, w + 20, h);
        g2d.drawLine(20, 0, 20, h);
        g2d.drawString("Last transactions", w / 2,  h + 15 );
        g2d.drawString("Min Value", 5,  h - 5 );
        g2d.drawString("Max Value", 5,  15 );
        drawChart();

    }

    public void drawChart(){
        var size = getSize();
        var insets = getInsets();
        int w = size.width - insets.left - insets.right - 20;
        int h = size.height - insets.top - insets.bottom - 20;

        Double min = Y.get(0);
        for(Double y : Y) min = Math.min(min, y);
        for(int i = 0; i < Y.size(); i++) {
            Y.set(i, Y.get(i) - min);
        }

        g2d.setColor(Color.BLUE);

        Double max = 0.0;
        for(Double y : Y) max = Math.max(max, y);
        int lx = 20;
        int ly = h - 20;
        for(int i = 0; i < Y.size(); i++) {
            Double x = Double.valueOf((i + 1) * w) / Double.valueOf(Y.size());
            Double y = Double.valueOf(Y.get(i) * h) / Double.valueOf(max);
            int ix = (int)Math.round(x);
            int iy = (int)Math.round(y );
            g2d.drawLine(lx, ly , ix + 20, h - iy);
            lx = ix + 20;
            ly = h - iy;
            //System.out.println(ix + " " +  iy);
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
