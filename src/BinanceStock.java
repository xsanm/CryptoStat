import org.json.JSONObject;

import java.io.IOException;

public class BinanceStock extends AbstractStockExchange {

    private  static String GET_URL = "https://api.binance.com/api/v1/ticker/price?symbol=";

    public BinanceStock() {

    }

    double getExchangePrice(Currency a, Currency b) throws IOException {
        GET_URL += a.name() + b.name();
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePrice(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.get("price"));
    }
}
