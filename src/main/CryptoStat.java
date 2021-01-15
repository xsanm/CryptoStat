import gui.*;
import objects.StockExchange;
import plot.Plot;
import plot.PlotWindow;
import stock.*;

import javax.swing.*;
import java.util.ArrayList;

public class CryptoStat {

    static ArrayList<StockExchange> stocks = new ArrayList<>();
    MainWindow mainWindow;

    
    private static void createStocks(MainWindow m) {
        JProgressBar b = new JProgressBar();
        b.setValue(0);
        b.setStringPainted(true);
        JFrame f = new JFrame();
        f.add(b);
        f.setTitle("Connecting with API");
        f.setSize(300, 100);
        f.setVisible(true);
        f.setLocation(300, 300);

        stocks.add(new BinanceStock());
        b.setValue(20);
        stocks.add(new BitBayStock());
        b.setValue(40);
        stocks.add(new CexioStock());
        b.setValue(60);
        stocks.add(new CoinbaseStock());
        b.setValue(80);
        stocks.add(new GeminiStock());
        b.setValue(100);

        f.setVisible(false);
    }

    public static void main(String[] args) {

        //PlotWindow pw = new PlotWindow(null, null);


        System.out.println("Hello CryptoStat");
        MainWindow mainWindow = new MainWindow();

        createStocks(mainWindow);

        mainWindow.addPanel("Exchange", new ExchangePanel(stocks, mainWindow));
        mainWindow.addPanel("Markets Data", new DataPanel(stocks, mainWindow));
        mainWindow.addPanel("Symbols", new SymbolsPanel(mainWindow));
        mainWindow.addPanel("Transaction Chart", new TransactionChartPanel(stocks));
    }


        



}
