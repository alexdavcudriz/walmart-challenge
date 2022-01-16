package com.demo.walmart.adapter.controller;

import com.demo.walmart.application.port.in.ProductsSearchQuery;
import com.demo.walmart.config.exception.BadRequestException;
import com.demo.walmart.utils.StubsFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Product controller unit tests")
@WebMvcTest(ProductsControllerAdapter.class)
@ComponentScan("org.springframework.cloud.sleuth.autoconfig")
public class ProductControllerTest implements StubsFactory {

    @Autowired
    private MockMvc requestor;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsSearchQuery productsQuery;

    @Test
    @DisplayName("Given the correct search value answer the products list")
    void givenTheCorrectSearchValueAnswerTheProductsList() throws Exception {

        final var productsList = buildProductsList();
        final var productsResponse = buildProductsResponseList();

        when(productsQuery.execute(anyString()))
                .thenReturn(productsList);

        requestor.perform(get(PRODUCTS_SEARCH_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(productsResponse)));

        verify(productsQuery, times(1)).execute(any());
    }

    @Test
    @DisplayName("Given short search value answer BadRequest")
    void givenShortSearchValueAnswerBadRequest() throws Exception {

        when(productsQuery.execute(anyString()))
                .thenThrow(new BadRequestException(INVALID_SEARCH_MESSAGE));

        requestor.perform(get(PRODUCTS_SHORT_SEARCH_VALUE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_SEARCH_MESSAGE));

        verify(productsQuery, times(1)).execute(any());
    }
}
