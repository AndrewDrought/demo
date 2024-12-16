package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "exchange_rate")
public class ExchangeRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="base_currency")
    private String baseCurrency;

    @Column(name="target_currency")
    private String targetCurrency;

    @Column(name="rate", precision = 5, scale = 2)
    private BigDecimal rate;

    @Column(name="fetched_at")
    private OffsetDateTime fetchedAt = OffsetDateTime.now();

    public ExchangeRateEntity() {}

    public ExchangeRateEntity(String baseCurrency, String targetCurrency, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.fetchedAt = OffsetDateTime.now();
    }

}
