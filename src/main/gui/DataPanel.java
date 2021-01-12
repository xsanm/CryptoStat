package gui;

import com.sun.tools.javac.Main;
import objects.StockExchange;
import stock.BinanceStock;

import javax.swing.*;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class DataPanel extends JPanel {

    ArrayList<StockExchange> stocks = new ArrayList<>();
    Set<String> allCurencies;
    String[] currencies;
    ArrayList<String> pairs;
    MainWindow mainWindow;

    public DataPanel(ArrayList<StockExchange> stocks, MainWindow m) {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Exchange Stocks and stats");
        this.add(title);
        this.stocks = stocks;
        System.out.println(stocks);
        mainWindow = m;
        createButtons();

    }

    private void createButtons(){
        JPanel buttonPanel = new JPanel();
        for(StockExchange se : stocks) {
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


    }

    private void chooseStock(StockExchange se) {

        //System.out.println(se.getStockName());
        try {
            allCurencies = se.getAllCurrencies();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            pairs = se.getAllPairs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createComboBox();
        System.out.println(allCurencies);
        System.out.println(pairs);
    }

    private void createComboBox(){
        this.add(new JLabel("Select base currency"));
        JPanel cbPanel = new JPanel();
        currencies =  allCurencies.toArray(new String[allCurencies.size()]);
        Arrays.sort(currencies);
        System.out.println(allCurencies);
        System.out.println(currencies);
        JComboBox cb = new JComboBox(currencies);
        cb.setSelectedItem("ETH");
        createTable("ETH");
        cb.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                createTable(String.valueOf(cb.getSelectedItem()));
            }
        });
        cbPanel.add(cb);
        this.add(cbPanel);

        this.repaint();
        mainWindow.repaint();

    }

    private void createTable(String base) {
        System.out.println(base);
        JPanel tPanel = new JPanel();
        String[] columnNames = { "Base Currency", "Dest Currency", "Value" };
        String[][] data = new String[currencies.length][5];
        for(int i = 0; i < currencies.length; i++) {
            data[i][0] = base;
            data[i][1] = currencies[i];
            if(!pairs.contains(base + currencies[i])) continue;
            try {
                data[i][2] = String.valueOf(((BinanceStock) stocks.get(0)).getExchangePrice(base, currencies[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DefaultTableModel model = new DefaultTableModel(data,columnNames);

        JTable table = new JTable(model);

        //table.setPreferredScrollableViewportSize(new Dimension(450,63));
        //table.setFillsViewportHeight(true);
        //.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //ScrollPane pane = new JScrollPane(table);

        //JScrollPane js = new JScrollPane(table);
        //js.setVisible(true);
        //tPanel.add(js);



        //JTable j = new JTable(data, columnNames);
        tPanel.add(table);
        tPanel.add(new JScrollPane(table));
        this.add(tPanel);

        this.repaint();
        mainWindow.repaint();
    }


}
