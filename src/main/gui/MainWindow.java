package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    JPanel jPanel = new JPanel();
    JTabbedPane tp = new JTabbedPane();

    public MainWindow() {
        this.setTitle("CryptoStat");
        this.setMinimumSize(new Dimension(800, 800));
        this.add(tp);
        this.setVisible(true);
        this.pack();
    }

    public void addPanel(String name_, JPanel o) {
        tp.add(name_, o);
    }
}
