package com.example.controller;

import com.example.dto.AddCurrencyRequest;
import com.example.dto.CurrencyResponse;
import com.example.dto.ExchangeRateResponse;
import com.example.service.CurrencyService;
import com.example.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    public CurrencyController(CurrencyService currencyService, ExchangeRateService exchangeRateService) {
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public CurrencyResponse getCurrencies() {
        return new CurrencyResponse(currencyService.getAllCurrencies());
    }

    @GetMapping("/{currency}/rates")
    public ExchangeRateResponse getCurrencyRates(@PathVariable("currency") String currency) {
        currencyService.getCurrencyOrThrow(currency);
        Map<String, BigDecimal> rates = exchangeRateService.getRatesForCurrency(currency);
        return new ExchangeRateResponse(currency.toUpperCase(), rates);
    }

    @PostMapping
    public void addCurrency(@RequestBody AddCurrencyRequest request) {
        currencyService.addCurrency(request.currencyCode());
    }
}