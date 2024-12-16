package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExternalApiClient {

    private final WebClient webClient;

    @Value("${external.api.access-key}")
    private String apiKey;

    public ExternalApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.exchangerate.host")
                .build();
    }

    public Map<String, BigDecimal> getRatesForCurrency(String baseCurrency) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/live")
                        .queryParam("access_key", apiKey)
                        .queryParam("source", baseCurrency.toUpperCase())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .<Map<String, Object>>handle((response, sink) -> {
                    if (!(boolean) response.get("success")) {
                        sink.error(new IllegalStateException("API error: " + response.get("error")));
                        return;
                    }
                    @SuppressWarnings("unchecked")
                    Map<String, Object> rates = (Map<String, Object>) response.get("quotes");
                    sink.next(rates);
                })
                .map(rates -> rates.entrySet().stream()
                        .collect(java.util.stream.Collectors.toMap(
                                entry -> entry.getKey().substring(3), // Видаляємо "USD" з ключа
                                entry -> new BigDecimal(entry.getValue().toString())
                        )))
                .block();
    }
}