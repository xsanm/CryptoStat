package stock;

import objects.AbstractStockExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BinanceStock extends AbstractStockExchange {

    private static String GET_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";

    public BinanceStock() {

    }

    public  double getExchangePrice(String a, String b) throws IOException {
        //GET_URL += a + b;
        System.out.println(GET_URL);
        String x = String.valueOf(super.getExchangePrice(GET_URL + a + b, a, b));
        System.out.println("X: " + x);
        if(x.equals("0.0")) {
            return 0.0;
            //System.out.println("sdgsh");
        }
        JSONObject obj1 = new JSONObject(x);
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.get("price"));
    }

    @Override
    public String getStockName() {
        return "Binance";
    }

    @Override
    public Set<String> getAllCurrencies() throws IOException {
        //GET_URL = "https://api.binance.com/api/v3/exchangeInfo";
        Set<String> res = new HashSet<String>();
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangeInfo("https://api.binance.com/api/v3/exchangeInfo")));
        JSONArray symbolList = obj1.getJSONArray("symbols");
        for (int i = 0; i < symbolList.length(); i++) {
            //System.out.println(symbolList.getJSONObject(i).get("baseAsset"));
            res.add(String.valueOf(symbolList.getJSONObject(i).get("baseAsset")));
        }

        return res;
    }

    @Override
    public ArrayList<String> getAllPairs() throws IOException {
        //TODO
        ArrayList<String> res = new ArrayList<>();
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangeInfo("https://api.binance.com/api/v3/exchangeInfo")));
        JSONArray symbolList = obj1.getJSONArray("symbols");
        for (int i = 0; i < symbolList.length(); i++) {
            //System.out.println(symbolList.getJSONObject(i).get("baseAsset"));
            res.add(String.valueOf(symbolList.getJSONObject(i).get("symbol")));
        }
        return res;
    }
}
