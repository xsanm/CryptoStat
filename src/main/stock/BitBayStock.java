package stock;

import objects.AbstractStockExchange;
import objects.Currency;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitBayStock extends AbstractStockExchange {

    private static String GET_URL = "https://api.bitbay.net/rest/trading/ticker/";

    public BitBayStock() {

    }

    public  double getExchangePrice(Currency a, Currency b) throws IOException {
        GET_URL += a.name() + "-" + b.name();
        //.out.println(GET_URL);
        JSONObject obj1 = new JSONObject(String.valueOf(super.getExchangePrice(GET_URL, a, b)));
        //System.out.println(obj1.toString());
        return Double.valueOf((String) obj1.getJSONObject("ticker").get("rate"));
    }
}
