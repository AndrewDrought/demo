package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code", unique = true, nullable = false)
    private String currencyCode;

    public CurrencyEntity() {
    }

    public CurrencyEntity(String currencyCode) {
        this.currencyCode = currencyCode.toUpperCase();
    }
}
