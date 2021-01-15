package gui;

import objects.StockExchange;
import plot.PlotWindow;
import stock.BitBayStock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TransactionChartPanel extends JPanel {
    ArrayList<StockExchange> stocks = new ArrayList<>();
    StockExchange choosedStock;

    public TransactionChartPanel(ArrayList<StockExchange> stocks) {
        this.stocks = stocks;

        createButtons();
    }

    private void createButtons(){
        JPanel buttonPanel = new JPanel();
        JButton jb = new JButton("Show Chart");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChart();
            }

        });
        buttonPanel.add(jb);

        this.add(buttonPanel);

        //this.add(new JLabel("Select base currency"));


    }
    private void showChart() {
        choosedStock = (BitBayStock) stocks.get(1);
        ArrayList<Double> Y = ((BitBayStock) choosedStock).getLastTransactions("BTC", "PLN", 100);

        PlotWindow plotWindow = new PlotWindow(null, Y);
    }

}
