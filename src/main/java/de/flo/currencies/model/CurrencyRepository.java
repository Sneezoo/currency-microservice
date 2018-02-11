package de.flo.currencies.model;

import java.util.Date;
import java.util.List;

public interface CurrencyRepository {

    Double getBySymbol(String symbol);
    List<String> getAllCurrencies();
    void putCurrency(String symbol, double value);
    void update(Date d);
    Date getLastUpdate();

}
