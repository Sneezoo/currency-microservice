package de.flo.currencies.controllers;

import de.flo.currencies.model.CurrencyRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
public class CurrencyController {

    @Resource
    CurrencyRepository currencyRepository;

    @RequestMapping("/currencies")
    @CrossOrigin(origins = "*")
    public List<String> currencies() {
        return currencyRepository.getAllCurrencies();
    }

    @RequestMapping("/currencies/{symbol}")
    @CrossOrigin(origins = "*")
    public double currency(@PathVariable("symbol") String symbol) {
        Double value = currencyRepository.getBySymbol(symbol);
        if (value == null) {
            throw new IllegalArgumentException("Currency " + symbol + " is not available");
        }
        return value;
    }
}
