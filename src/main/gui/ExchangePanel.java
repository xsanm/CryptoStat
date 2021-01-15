package gui;

import objects.StockExchange;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ExchangePanel extends JPanel {
    ArrayList<StockExchange> stocks;
    Set<String> currenciesList;
    String[] currencies;
    MainWindow mainWindow;
    String first = "ETH";
    String second = "BTC";
    JPanel cbPanel = new JPanel();
    JPanel tPanel = new JPanel();
    Double firstValue = 1.0;
    Double secondValue = 1.0;


    public ExchangePanel(ArrayList<StockExchange> stocks, MainWindow m) {
        this.stocks = stocks;
        this.mainWindow = m;

        currenciesList = new TreeSet<>();
        for (StockExchange se : stocks) currenciesList.addAll(se.getAllCurrencies());
        currencies = currenciesList.toArray(new String[currenciesList.size()]);

        JLabel title = new JLabel("Exchange markets");
        this.add(title);

        createComboBoxes();
    }

    private void createComboBoxes() {
        cbPanel.removeAll();

        JSpinner spin1 = new JSpinner(new SpinnerNumberModel((double) firstValue, 0.0, 1000000.0, 1.0));
        JSpinner spin2 = new JSpinner(new SpinnerNumberModel((double) secondValue, 0.0, 1000000.0, 1.0));

        JComboBox cb1 = new JComboBox(currencies);
        cb1.setSelectedItem(first);
        cb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                first = String.valueOf(cb1.getSelectedItem());
                findExchange();
            }
        });

        JComboBox cb2 = new JComboBox(currencies);
        cb2.setSelectedItem(second);
        cb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                second = String.valueOf(cb2.getSelectedItem());
                findExchange();
            }
        });

        JButton ebtn = new JButton("Exchange");
        ebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstValue = (Double) spin1.getValue();
                secondValue = (Double) spin2.getValue();
                findExchange();
            }
        });

        cbPanel.add(cb1);
        cbPanel.add(spin1);
        cbPanel.add(cb2);
        cbPanel.add(spin2);
        cbPanel.add(ebtn);
        this.add(cbPanel);
        this.repaint();
        mainWindow.repaint();
        findExchange();
    }

    private void findExchange() {
        JTable table;
        tPanel.removeAll();
        String[] columnNames = {
                "Stock",
                "Rate",
                first + " -> " + second,
                "Diff to best",
                second + " -> " + first,
                "Diff to best"
        };

        ArrayList<String[]> list = new ArrayList<>();
        for (StockExchange se : stocks) {
            String[] row = new String[6];
            row[0] = se.getStockName();
            try {
                row[1] = se.getExchangePrice(first, second);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (row[1].equals("-")) {
                row[2] = "-";
                row[4] = "-";
            } else {
                row[2] = String.valueOf(firstValue * Double.valueOf(row[1]));
                row[4] = String.valueOf(secondValue * Double.valueOf(row[1]));
            }

            list.add(row);
        }


        String[][] data = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }

        double min = 0.0;
        for (int i = 0; i < 5; i++)
            if (!data[i][2].equals("-"))
                min = Math.max(min, Double.parseDouble(data[i][2]));
        for (int i = 0; i < 5; i++)
            if (!data[i][2].equals("-")) {
                data[i][3] = String.format("%.5f", min - Double.parseDouble(data[i][2]), 6);
            }

        min = 0.0;
        for (int i = 0; i < 5; i++)
            if (!data[i][4].equals("-"))
                min = Math.max(min, Double.parseDouble(data[i][4]));
        for (int i = 0; i < 5; i++)
            if (!data[i][4].equals("-"))
                data[i][5] = String.format("%.5f", min - Double.parseDouble(data[i][4]));

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);
        table.setBounds(30, 40, 200, 300);

        table.setRowHeight(30);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tPanel.add(table);
        tPanel.add(new JScrollPane(table));

        this.add(tPanel);
        this.repaint();
        mainWindow.repaint();
    }

}
