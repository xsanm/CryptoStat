package objects;

import objects.Currency;

import java.io.IOException;

public interface StockExchange {
    public Object getExchangePrice(String GET_URL, Currency a, Currency b) throws IOException;
}
