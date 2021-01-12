import java.io.IOException;

public interface StockExchange {
    Object getExchangePrice(String GET_URL, Currency a, Currency b) throws IOException;
}
