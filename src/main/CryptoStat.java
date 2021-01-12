import gui.DataPanel;
import gui.MainWindow;
import objects.StockExchange;
import stock.*;

import java.util.ArrayList;

public class CryptoStat {

    static ArrayList<StockExchange> stocks = new ArrayList<>();

    private static void createStocks() {
        stocks.add(new BinanceStock());
        stocks.add(new BitBayStock());
        stocks.add(new CexioStock());
        stocks.add(new CoinbaseStock());
        stocks.add(new GeminiStock());
    }

    public static void main(String[] args) {
        createStocks();
        System.out.println("Hello CryptoStat");
        MainWindow mainWindow = new MainWindow();
        mainWindow.addPanel("Stocks Data", new DataPanel(stocks, mainWindow));
    }

        



}
