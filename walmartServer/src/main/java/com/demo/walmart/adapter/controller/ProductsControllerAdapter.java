package com.demo.walmart.adapter.controller;

import com.demo.walmart.adapter.controller.model.ProductsResponse;
import com.demo.walmart.application.port.in.ProductsSearchQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1.0")
public class ProductsControllerAdapter {

    private static final String SEARCH_ENDPOINT = "products/{value}";

    @Autowired
    private ProductsSearchQuery productsQuery;

    @GetMapping(SEARCH_ENDPOINT)
    public List<ProductsResponse> search(@PathVariable String value) {
        log.info("Recibiendo llamada en el endpoint: /api/v1.0/{}", SEARCH_ENDPOINT);
        return ProductsResponse.of(productsQuery.execute(value));
    }
}
