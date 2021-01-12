package stock;

import objects.AbstractStockExchange;
import objects.Currency;
import org.json.JSONObject;

import java.io.IOException;

public class CexioStock extends AbstractStockExchange {
    private static String GET_URL = "https://cex.io/api/ticker/";


    public double getExchangePrice(Currency a, Currency b) throws IOException {
        GET_URL += a.name() + "/" + b.name();
        //.out.println(GET_URL);
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePrice(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.get("last"));
    }
}
