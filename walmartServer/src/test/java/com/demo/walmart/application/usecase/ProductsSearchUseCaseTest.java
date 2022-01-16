package com.demo.walmart.application.usecase;

import com.demo.walmart.adapter.repository.ProductsRepository;
import com.demo.walmart.config.exception.BadRequestException;
import com.demo.walmart.utils.StubsFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Product search use case unit tests")
@ExtendWith(MockitoExtension.class)
public class ProductsSearchUseCaseTest implements StubsFactory {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsSearchUseCase productsSearchUseCase;

    @Test
    @DisplayName("Given the correct search value answer the products list")
    void givenTheCorrectSearchValueAnswerTheProductsList() {

        when(productsRepository.findByBrandOrDescription(anyString()))
                .thenReturn(buildProductsList());

        final var result = productsSearchUseCase.execute(description);

        assertNotNull(result);
        assertEquals(result.get(0), buildProductsList().get(0));
        assertEquals(result.get(0).getBrand(), buildProductsList().get(0).getBrand());
        assertEquals(result.get(0).getDescription(), buildProductsList().get(0).getDescription());
        assertIterableEquals(result, buildProductsList());
    }

    @Test
    @DisplayName("Given a short search value throws BadRequestException")
    void givenAShortSearchValueThrowsBadRequestException() {

        assertThatThrownBy(() -> productsSearchUseCase.execute(brand))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Given a palindrome search value answer the products list with discount")
    void givenAPalindromeSearchValueAnswerTheProductsListWithDiscount() {

        when(productsRepository.findByBrandOrDescription(anyString()))
                .thenReturn(buildProductsList());

        final var result = productsSearchUseCase.execute(PALINDROME_SEARCH_VALUE);

        assertNotNull(result);
        assertEquals(buildProductsList().get(0).doDiscount().getPrice(), result.get(0).getPrice());
    }

    @Test
    @DisplayName("Given a numeric palindrome search value answer the products list with discount")
    void givenANumericPalindromeSearchValueAnswerTheProductsListWithDiscount() {

        when(productsRepository.findById(anyInt()))
                .thenReturn(buildProductsList());

        final var result = productsSearchUseCase.execute(NUMERIC_PALINDROME_SEARCH_VALUE);

        assertNotNull(result);
        assertEquals(buildProductsList().get(0).doDiscount().getPrice(), result.get(0).getPrice());
    }
}
