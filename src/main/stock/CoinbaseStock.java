package stock;

import objects.AbstractStockExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CoinbaseStock extends AbstractStockExchange {
    private static String GET_URL = "https://api.coinbase.com/v2/exchange-rates?currency=";
    private ArrayList<String> currenciesList = new ArrayList<>();
    private ArrayList<String> exchangePairs = new ArrayList<>();

    public CoinbaseStock() {
        JSONObject exInfo = null;
        try {
            exInfo = new JSONObject((String) super.getExchangeInfo("https://api.coinbase.com/v2/currencies"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray infoList = exInfo.getJSONArray("data");
        for (int i = 0; i < infoList.length(); i++) {
            String curr = String.valueOf(infoList.getJSONObject(i).get("id"));
            currenciesList.add(curr);
        }
    }

    public String getExchangePrice(String a, String b) throws IOException {
        if (!exchangePairs.contains(a + b)) return "-";
        JSONObject response = super.getExchangePriceObject(GET_URL, a, b);
        if (response == null) return "-";
        JSONObject obj1 = new JSONObject(String.valueOf(response));
        return String.valueOf(obj1.getJSONObject("data").getJSONObject("rates").get(b));
    }

    @Override
    public String getStockName() {
        return "Coinbase";
    }

    @Override
    public ArrayList<String> getAllCurrencies() {
        return currenciesList;
    }

    @Override
    public ArrayList<String> getAllPairs() {
        return exchangePairs;
    }

    @Override
    public ArrayList<String[]> generateExchangeTable(String base) {
        currenciesList = this.getAllCurrencies();
        String[] currencies = currenciesList.toArray(new String[currenciesList.size()]);

        Arrays.sort(currencies);
        ArrayList<String[]> list = new ArrayList<>();

        JSONObject exInfo = null;
        try {
            exInfo = new JSONObject((String) super.getExchangeInfo("https://api.coinbase.com/v2/exchange-rates?currency=" + base));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject dataList = exInfo.getJSONObject("data");
        JSONObject infoList = dataList.getJSONObject("rates");
        Iterator<String> keys = infoList.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String[] row = new String[3];
            row[0] = base;
            row[1] = key;
            row[2] = String.valueOf(infoList.get(key));
            list.add(row);
        }
        return list;
    }
}
