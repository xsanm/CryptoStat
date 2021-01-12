package objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public interface StockExchange {
    Object getExchangePrice(String GET_URL, String a, String b) throws IOException;
    String getStockName();
    Set<String> getAllCurrencies() throws IOException;
    ArrayList<String> getAllPairs() throws IOException;
}
