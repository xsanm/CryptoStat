package stock;

import objects.AbstractStockExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CexioStock extends AbstractStockExchange {
    private static String GET_URL = "https://cex.io/api/ticker/";


    public String getExchangePrice(String a, String b) throws IOException {
        GET_URL += a + "/" + b;
        //.out.println(GET_URL);
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePriceObject(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return (String) obj1.get("last");
    }

    @Override
    public String getStockName() {
        return "CexIO";
    }

    @Override
    public ArrayList<String> getAllCurrencies(){
        return null;
    }

    @Override
    public ArrayList<String> getAllPairs() {
        return null;
    }

    @Override
    public ArrayList<String[]> generateExchangeTable(String base) {
        return null;
    }
}
