package gui;

import com.sun.tools.javac.Main;
import objects.AbstractStockExchange;
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
    StockExchange choosedStock;
    ArrayList<String> currenciesList;
    //ArrayList<String> pairsList;
    String[] currencies;
    //String[] pairs;
    MainWindow mainWindow;
    JTable table;
    JPanel cbPanel = new JPanel();
    JPanel tPanel = new JPanel();


    public DataPanel(ArrayList<StockExchange> stocks, MainWindow m) {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Stocks markets");
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

        this.add(new JLabel("Select base currency"));


    }

    private void chooseStock(StockExchange se) {
        choosedStock = se;
        currenciesList = se.getAllCurrencies();
        currencies = currenciesList.toArray(new String[currenciesList.size()]);

        //pairsList = se.getAllPairs();
        //pairs =  pairsList.toArray(new String[pairsList.size()]);


        Arrays.sort(currencies);
        //Arrays.sort(pairs);

        //System.out.println(pairs);
        //System.out.println(currencies);

        createComboBox();

    }

    private void createComboBox(){

        cbPanel.removeAll();
        ///cbPanel = new JPanel();

        JComboBox cb = new JComboBox(currencies);
        cb.setSelectedItem("ETH");
        cb.addActionListener(new ActionListener () {
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
        String[] columnNames = { "Base Currency", "Dest Currency", "Value" };

        ArrayList<String[]> list = choosedStock.generateExchangeTable(base);
        /*for(int i = 0; i < currencies.length; i++) {

            if(!pairsList.contains(base + currencies[i])) continue;

            String[] row = new String[3];
            row[0] = base;
            row[1] = currencies[i];
            try {
                row[2] = String.valueOf(
                         choosedStock.getExchangePrice(base, currencies[i])
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(row);
        }*/
        
        String[][] data = new String[list.size()][5];
        for(int i = 0; i < list.size(); i++) {
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
