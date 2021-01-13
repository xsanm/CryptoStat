package stock;

import objects.AbstractStockExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CexioStock extends AbstractStockExchange {
    private static String GET_URL = "https://cex.io/api/ticker/";
    private ArrayList<String> currenciesList = new ArrayList<>();
    private ArrayList<String> exchangePairsList = new ArrayList<>();

    public CexioStock() {
        JSONObject exInfo = null;
        try {
            exInfo = new JSONObject((String) super.getExchangeInfo("https://cex.io/api/currency_limits"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray infoList = (exInfo.getJSONObject("data")).getJSONArray("pairs");
        for (int i = 0; i < infoList.length(); i++) {
            JSONObject curr =infoList.getJSONObject(i);
            String a = String.valueOf(curr.get("symbol1"));
            String b = String.valueOf(curr.get("symbol2"));
            if(!currenciesList.contains(a)) currenciesList.add(a);
            if(!currenciesList.contains(b)) currenciesList.add(b);
            exchangePairsList.add(a + b);
        }
    }

    public String getExchangePrice(String a, String b) throws IOException {
        //GET_URL += a + "/" + b;
        //.out.println(GET_URL);
        if(!exchangePairsList.contains(a + b)) return "-";
        JSONObject response = super.getExchangePriceObject(GET_URL + a + "/" + b, a, b);
        if(response == null) return "-";
        JSONObject obj1 = new JSONObject(String.valueOf(response));
        //System.out.println(obj1.toString());
        return (String) obj1.get("last");
    }

    @Override
    public String getStockName() {
        return "CexIO";
    }

    @Override
    public ArrayList<String> getAllCurrencies(){
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
        String[] pairs =  exchangePairsList.toArray(new String[exchangePairsList.size()]);


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
