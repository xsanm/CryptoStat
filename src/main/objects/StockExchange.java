package objects;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public interface StockExchange {
    JSONObject getExchangePriceObject(String GET_URL, String a, String b) throws IOException;

    String getExchangePrice(String a, String b) throws IOException;

    String getStockName();

    ArrayList<String> getAllCurrencies();

    ArrayList<String> getAllPairs();

    ArrayList<String[]> generateExchangeTable(String base);


}
