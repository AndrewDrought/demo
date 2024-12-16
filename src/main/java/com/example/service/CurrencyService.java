package com.example.service;

import com.example.entity.CurrencyEntity;
import com.example.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.repository.CurrencyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<String> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(CurrencyEntity::getCurrencyCode)
                .toList();
    }

    @Transactional
    public void addCurrency(String currencyCode) {
        String code = currencyCode.toUpperCase();
        currencyRepository.findByCurrencyCode(code).ifPresent(c -> {
            throw new RuntimeException("Currency already exists: " + code);
        });
        currencyRepository.save(new CurrencyEntity(code));
    }

    public CurrencyEntity getCurrencyOrThrow(String code) {
        return currencyRepository.findByCurrencyCode(code.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Currency not found: " + code));
    }
}