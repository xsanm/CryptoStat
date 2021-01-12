package stock;

import objects.AbstractStockExchange;
import objects.Currency;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class CoinbaseStock extends AbstractStockExchange {
    private  static String GET_URL = "https://api.coinbase.com/v2/exchange-rates?currency=";

    public double getExchangePrice(String a, String b) throws IOException {
        GET_URL += a;
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePrice(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.getJSONObject("data").getJSONObject("rates").get(b));
    }

    @Override
    public String getStockName() {
        return "Coinbase";
    }

    @Override
    public Set<String> getAllCurrencies() throws IOException {
        return null;
    }

    @Override
    public ArrayList<String> getAllPairs() throws IOException {
        return null;
    }
}
