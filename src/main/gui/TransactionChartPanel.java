package gui;

import plot.PlotWindow;
import stock.BitBayStock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TransactionChartPanel extends JPanel {
    BitBayStock stock;
    ArrayList<String> currencies;
    String[] pairs;
    String selectedPair = "BTC-PLN";
    int selectedValue = 200;

    public TransactionChartPanel() {
        this.stock = new BitBayStock();
        ArrayList<String> pairs1 = stock.getAllPairs();
        currencies = stock.getAllCurrencies();
        pairs = new String[pairs1.size()];
        int j = 0;
        for (String p : pairs1) {
            for (int i = 1; i < pairs1.size(); i++) {
                if (currencies.contains(p.substring(0, i))) {
                    pairs[j] = (p.substring(0, i) + "-" + p.substring(i));
                    j++;
                    break;
                }
            }
        }
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(new JLabel("Chart of last transaction (from min rate to max) from BitBay"));
        this.add(new JLabel("Select number of last transactions and market pair"));
        createButtons();
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();

        JComboBox cb = new JComboBox(pairs);
        cb.setSelectedItem(selectedPair);
        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPair = String.valueOf(cb.getSelectedItem());
            }
        });

        JSpinner spin1 = new JSpinner(new SpinnerNumberModel(selectedValue, 10, 300, 10));

        buttonPanel.add(spin1);
        buttonPanel.add(cb);

        JButton jb = new JButton("Show Chart");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedValue = (int) spin1.getValue();
                showChart();
            }

        });
        buttonPanel.add(jb);
        this.add(buttonPanel);
    }

    private void showChart() {
        int i = selectedPair.indexOf('-');
        ArrayList<Double> Y = stock.getLastTransactions(selectedPair.substring(0, i), selectedPair.substring(i + 1), selectedValue);
        new PlotWindow(null, Y);
    }

}
