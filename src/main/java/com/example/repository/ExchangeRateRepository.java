package com.example.repository;

import com.example.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    List<ExchangeRateEntity> findByBaseCurrency(String baseCurrency);
}