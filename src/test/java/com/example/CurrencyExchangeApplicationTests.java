package com.example;

import com.example.entity.CurrencyEntity;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CurrencyExchangeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testAddCurrency() {
		CurrencyRepository repository = Mockito.mock(CurrencyRepository.class);
		CurrencyService service = new CurrencyService(repository);

		Mockito.when(repository.findByCurrencyCode("USD")).thenReturn(Optional.empty());

		service.addCurrency("USD");

		Mockito.verify(repository).save(Mockito.any(CurrencyEntity.class));
	}

	@Test
	void testAddDuplicateCurrency() {
		CurrencyRepository repository = Mockito.mock(CurrencyRepository.class);
		CurrencyService service = new CurrencyService(repository);

		Mockito.when(repository.findByCurrencyCode("USD")).thenReturn(Optional.of(new CurrencyEntity("USD")));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> service.addCurrency("USD"));
		assertTrue(ex.getMessage().contains("already exists"));
	}

}
