package com.example.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateResponse(String baseCurrency, Map<String, BigDecimal> rates) {
}