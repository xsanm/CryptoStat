package stock;

import objects.AbstractStockExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BinanceStock extends AbstractStockExchange {

    private static String GET_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";
    private ArrayList<String> currenciesList = new ArrayList<>();
    private ArrayList<String> exchangePairsList = new ArrayList<>();

    public BinanceStock() {
        JSONObject exInfo = null;
        try {
            exInfo = new JSONObject((String) super.getExchangeInfo("https://api.binance.com/api/v3/exchangeInfo"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray infoList = exInfo.getJSONArray("symbols");
        for (int i = 0; i < infoList.length(); i++) {
            String curr = String.valueOf(infoList.getJSONObject(i).get("baseAsset"));
            if(!currenciesList.contains(curr)) currenciesList.add(curr);

            String pair = String.valueOf(infoList.getJSONObject(i).get("symbol"));
            exchangePairsList.add(pair);
        }
    }

    public String getExchangePrice(String a, String b) throws IOException {
        //GET_URL += a + b;
        //System.out.println(GET_URL);
        //String x = String.valueOf(super.getExchangePrice(GET_URL + a + b, a, b));
        //System.out.println("X: " + x);

        if(!exchangePairsList.contains(a + b)) return "-";
        JSONObject response = super.getExchangePriceObject(GET_URL + a + b, a, b);
        if(response == null) return "-";
    
        JSONObject obj1 = response;
        
        if(obj1 == null) return "-";

        //System.out.println(obj1.toString());
        return String.valueOf(obj1.get("price"));
    }

    @Override
    public String getStockName() {
        return "Binance";
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
        String[] pairs =  exchangePairsList.toArray(new String[exchangePairsList.size()]);

        //TODO
        Arrays.sort(currencies);
        Arrays.sort(pairs);
        ArrayList<String[]> list = new ArrayList<>();
        for(int i = 0; i < currencies.length; i++) {

            if(!exchangePairsList.contains(base + currencies[i])) continue;

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
