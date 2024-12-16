package com.example.service;

import com.example.entity.ExchangeRateEntity;
import org.springframework.stereotype.Service;
import com.example.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final Map<String, Map<String, BigDecimal>> ratesCache = new ConcurrentHashMap<>();

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void updateRates(String baseCurrency, Map<String, BigDecimal> newRates) {
        List<ExchangeRateEntity> entities = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : newRates.entrySet()) {
            entities.add(new ExchangeRateEntity(baseCurrency, entry.getKey(), entry.getValue()));
        }
        exchangeRateRepository.saveAll(entities);

        ratesCache.put(baseCurrency, newRates);
    }

    public Map<String, BigDecimal> getRatesForCurrency(String baseCurrency) {
        Map<String, BigDecimal> cached = ratesCache.get(baseCurrency.toUpperCase());
        if (cached == null) {
            List<ExchangeRateEntity> fromDb = exchangeRateRepository.findByBaseCurrency(baseCurrency.toUpperCase());
            if(!fromDb.isEmpty()) {
                Map<String, BigDecimal> map = new HashMap<>();
                fromDb.forEach(e -> map.put(e.getTargetCurrency(), e.getRate()));
                ratesCache.put(baseCurrency.toUpperCase(), map);
                return map;
            }
            return Collections.emptyMap();
        }
        return cached;
    }
}
