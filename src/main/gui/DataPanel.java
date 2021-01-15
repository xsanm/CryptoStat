package gui;

import objects.StockExchange;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DataPanel extends JPanel {

    ArrayList<StockExchange> stocks;
    ArrayList<String> currenciesList;
    StockExchange choosedStock;
    String[] currencies;
    MainWindow mainWindow;
    JTable table;
    JPanel cbPanel = new JPanel();
    JPanel tPanel = new JPanel();


    public DataPanel(ArrayList<StockExchange> stocks, MainWindow m) {
        this.stocks = stocks;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Stocks markets");
        this.add(title);

        mainWindow = m;
        createButtons();
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        for (StockExchange se : stocks) {
            JButton jb = new JButton(se.getStockName());
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseStock(se);
                }
            });

            buttonPanel.add(jb);
        }
        this.add(buttonPanel);

        this.add(new JLabel("Select base currency"));

    }

    private void chooseStock(StockExchange se) {
        choosedStock = se;
        currenciesList = se.getAllCurrencies();
        currencies = currenciesList.toArray(new String[currenciesList.size()]);

        Arrays.sort(currencies);

        createComboBox();
    }

    private void createComboBox() {
        cbPanel.removeAll();

        JComboBox cb = new JComboBox(currencies);
        cb.setSelectedItem("ETH");
        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTable(String.valueOf(cb.getSelectedItem()));
            }
        });

        cbPanel.add(cb);
        this.add(cbPanel);
        createTable("ETH");
        this.repaint();
        mainWindow.repaint();
    }

    private void createTable(String base) {
        tPanel.removeAll();
        String[] columnNames = {"Base Currency", "Dest Currency", "Rate"};

        ArrayList<String[]> list = choosedStock.generateExchangeTable(base);

        String[][] data = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);

        tPanel.add(table);
        tPanel.add(new JScrollPane(table));
        this.add(tPanel);
        this.repaint();
        mainWindow.repaint();
    }

}
