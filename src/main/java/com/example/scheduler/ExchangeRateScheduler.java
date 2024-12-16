package com.example.scheduler;

import com.example.service.CurrencyService;
import com.example.service.ExchangeRateService;
import com.example.service.ExternalApiClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class ExchangeRateScheduler {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;
    private final ExternalApiClient externalApiClient;

    public ExchangeRateScheduler(CurrencyService currencyService,
                                 ExchangeRateService exchangeRateService,
                                 ExternalApiClient externalApiClient) {
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
        this.externalApiClient = externalApiClient;
    }

    // Every hour
    @Scheduled(cron = "${scheduler.cron}")
    public void updateAllRates() {
        List<String> allCurrencies = currencyService.getAllCurrencies();
        for (String base : allCurrencies) {
            Map<String, BigDecimal> rates = externalApiClient.getRatesForCurrency(base);
            exchangeRateService.updateRates(base, rates);
        }
    }
}