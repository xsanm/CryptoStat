package stock;

import objects.AbstractStockExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GeminiStock extends AbstractStockExchange {
    private static String GET_URL = "https://api.gemini.com/v1/pubticker/";
    private ArrayList<String> currenciesList = new ArrayList<>();
    private ArrayList<String> exchangePairsList = new ArrayList<>();

    public GeminiStock() {
        JSONArray exInfo = null;
        try {
            exInfo = new JSONArray((String) super.getExchangeInfo("https://api.gemini.com/v1/symbols"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < exInfo.length(); i++) {
            String curr = String.valueOf(exInfo.get(i));
            exchangePairsList.add(curr.toUpperCase());
        }
        for (String p : exchangePairsList) {
            JSONObject det = null;
            try {
                det = new JSONObject((String) super.getExchangeInfo("https://api.gemini.com/v1/symbols/details/" + p));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String a = String.valueOf(det.get("base_currency"));
            String b = String.valueOf(det.get("quote_currency"));
            if (!currenciesList.contains(a)) currenciesList.add(a);
            if (!currenciesList.contains(b)) currenciesList.add(b);
        }
    }

    public String getExchangePrice(String a, String b) throws IOException {
        if (!exchangePairsList.contains(a + b)) return "-";
        JSONObject response = super.getExchangePriceObject(GET_URL + a + b, a, b);
        if (response == null) return "-";
        JSONObject obj1 = new JSONObject(String.valueOf(response));
        return (String) obj1.get("bid");
    }

    @Override
    public String getStockName() {
        return "Gemini";
    }

    @Override
    public ArrayList<String> getAllCurrencies() {
        return currenciesList;
    }

    @Override
    public ArrayList<String> getAllPairs() {
        return exchangePairsList;
    }

    @Override
    public ArrayList<String[]> generateExchangeTable(String base) {
        currenciesList = this.getAllCurrencies();
        String[] currencies = currenciesList.toArray(new String[currenciesList.size()]);

        exchangePairsList = this.getAllPairs();
        Arrays.sort(currencies);
        ArrayList<String[]> list = new ArrayList<>();


        for (int i = 0; i < currencies.length; i++) {

            if (!exchangePairsList.contains(base + currencies[i])) continue;

            String[] row = new String[3];
            row[0] = base;
            row[1] = currencies[i];
            try {
                row[2] = String.valueOf(
                        this.getExchangePrice(base, currencies[i])
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(row);
        }
        return list;
    }

}
