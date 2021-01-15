package stock;

import objects.AbstractStockExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BitBayStock extends AbstractStockExchange {

    private static String GET_URL = "https://api.bitbay.net/rest/trading/ticker/";
    private ArrayList<String> currenciesList = new ArrayList<>();
    private ArrayList<String> exchangePairsList = new ArrayList<>();

    public BitBayStock() {
        JSONObject exInfo = null;
        try {
            exInfo = new JSONObject((String) super.getExchangeInfo("https://api.bitbay.net/rest/trading/ticker"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject infoList = exInfo.getJSONObject("items");
        Iterator<String> keys = infoList.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            if (infoList.get(key) instanceof JSONObject) {
                JSONObject market = (JSONObject) ((JSONObject) infoList.get(key)).get("market");
                JSONObject first = (JSONObject) market.get("first");
                JSONObject second = (JSONObject) market.get("second");
                String a = (String) first.get("currency");
                String b = (String) second.get("currency");
                if (!currenciesList.contains(a)) currenciesList.add(a);
                if (!currenciesList.contains(b)) currenciesList.add(b);
                exchangePairsList.add(a + b);
            }
        }
    }

    public String getExchangePrice(String a, String b) throws IOException {
        if (!exchangePairsList.contains(a + b)) return "-";
        JSONObject response = super.getExchangePriceObject(GET_URL + a + "-" + b, a, b);
        if (response == null) return "-";
        JSONObject obj1 = new JSONObject(String.valueOf(response));
        return (String) obj1.getJSONObject("ticker").get("rate");
    }

    @Override
    public String getStockName() {
        return "BitBay";
    }

    @Override
    public ArrayList<String> getAllCurrencies() {
        return currenciesList;
    }

    @Override
    public ArrayList<String> getAllPairs() {
        return exchangePairsList;
    }

    public ArrayList<String[]> generateExchangeTable(String base) {
        currenciesList = this.getAllCurrencies();
        String[] currencies = currenciesList.toArray(new String[currenciesList.size()]);

        exchangePairsList = this.getAllPairs();
        String[] pairs = exchangePairsList.toArray(new String[exchangePairsList.size()]);


        Arrays.sort(currencies);
        Arrays.sort(pairs);
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

    public ArrayList<Double> getLastTransactions(String a, String b, int number) {
        JSONObject exInfo = null;
        String URL = "https://api.bitbay.net/rest/trading/transactions/" + a + "-" + b + "?limit=" + number;
        try {
            exInfo = new JSONObject((String) super.getExchangeInfo(URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Double> res = new ArrayList<>();

        JSONArray infoList = exInfo.getJSONArray("items");
        for (int i = 0; i < infoList.length(); i++) {
            JSONObject curr = infoList.getJSONObject(i);
            String r = String.valueOf(curr.get("r"));
            res.add(Double.valueOf(r));

        }
        return res;
    }
}
