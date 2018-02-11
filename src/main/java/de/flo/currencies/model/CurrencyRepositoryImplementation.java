package de.flo.currencies.model;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CurrencyRepositoryImplementation implements CurrencyRepository{

    private Map<String, Double> currencyMap = new HashMap<>();
    private Date lastUpdate;

    public CurrencyRepositoryImplementation() throws ParseException {
        lastUpdate = new SimpleDateFormat("dd-mm-yyyy").parse("01-01-1980");
    }

    @Override
    public Double getBySymbol(String symbol) {
        return currencyMap.get(symbol);
    }

    @Override
    public List<String> getAllCurrencies() {
        return Arrays.asList(currencyMap.keySet().toArray(new String[0]));
    }

    @Override
    public void putCurrency(String symbol, double value) {
        currencyMap.put(symbol, value);
    }

    @Override
    public void update(Date d) {
        lastUpdate = d;
    }

    @Override
    public Date getLastUpdate() {
        return lastUpdate;
    }
}
