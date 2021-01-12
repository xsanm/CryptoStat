package stock;

import objects.AbstractStockExchange;
import objects.Currency;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class GeminiStock extends AbstractStockExchange {
    private  static String GET_URL = "https://api.gemini.com/v1/pubticker/";

    public double getExchangePrice(String a, String b) throws IOException {
        GET_URL += a + b;
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePrice(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.get("bid"));
    }

    @Override
    public String getStockName() {
        return "Gemini";
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
