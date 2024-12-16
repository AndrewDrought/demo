package com.example;

import com.example.controller.CurrencyController;
import com.example.service.CurrencyService;
import com.example.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    void testGetCurrenciesEmpty() throws Exception {
        when(currencyService.getAllCurrencies()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRates() throws Exception {
        when(currencyService.getCurrencyOrThrow("USD")).thenReturn(null);
        when(exchangeRateService.getRatesForCurrency("USD")).thenReturn(Map.of("EUR", BigDecimal.valueOf(0.9)));
        mockMvc.perform(get("/api/currencies/USD/rates"))
                .andExpect(status().isOk());
    }
}
