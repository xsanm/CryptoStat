package objects;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractStockExchange implements StockExchange {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static String GET_URL = "https://api.bitbay.net/rest/trading/ticker/";
    @Override
    public Object getExchangePrice(String GET_URL, Currency a, Currency b) throws IOException {

        //GET_URL += a.name() + "-" + b.name();
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());

            return response;


            //JSONObject obj1 = new JSONObject(response.toString());
            // resp = obj1.getJSONObject("ticker");

            //System.out.println(resp.get("rate"));
            //return  resp.get("rate");

        } else {
            System.out.println("GET request not worked");
            return 0.0;
        }
    }
}
