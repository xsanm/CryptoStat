import org.json.JSONObject;

import java.io.IOException;

public class CoinbaseStock extends AbstractStockExchange {
    private  static String GET_URL = "https://api.coinbase.com/v2/exchange-rates?currency=";

    double getExchangePrice(Currency a, Currency b) throws IOException {
        GET_URL += a.name();
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePrice(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.getJSONObject("data").getJSONObject("rates").get(b.name()));
    }
}
